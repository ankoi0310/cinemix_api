package vn.edu.hcmuaf.fit.cinemix_api.service.booking;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.utils.PayOSUtil;
import vn.edu.hcmuaf.fit.cinemix_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.payment.payos.LinkCreationResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.payment.payos.PayOSResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.seat.SeatDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.InvoiceMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.invoice.InvoiceRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.seat.SeatRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.showtime.ShowtimeRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.ticket.TicketRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;
import vn.edu.hcmuaf.fit.cinemix_api.utils.AppUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Value("${payos.url}")
    public String PAYOS_API_URL;

    @Value("${payos.client-id}")
    public String PAYOS_CLIENT_ID;

    @Value("${payos.api-key}")
    public String PAYOS_API_KEY;

    @Value("${payos.checksum-key}")
    public String PAYOS_CHECKSUM_KEY;

    @Value("${payos.return-url}")
    public String PAYOS_RETURN_URL;

    @Value("${payos.cancel-url}")
    public String PAYOS_CANCEL_URL;

    private final InvoiceRepository invoiceRepository;
    private final TicketRepository ticketRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public LinkCreationResponse createBooking(BookingRequest request) throws BaseException, IOException {
        try {

            String email = AppUtils.getCurrentUsername();
            AppUser appUser = userRepository.findByEmail(email)
                                            .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản"));

            Showtime showtime = showtimeRepository.findById(request.getShowtime().getId())
                                                  .orElseThrow(() -> new NotFoundException("Không tìm thấy lịch " +
                                                          "chiếu"));

            List<Ticket> tickets = new ArrayList<>();
            for (SeatDTO seatDTO : request.getSeats()) {
                Seat seat = seatRepository.findById(seatDTO.getId())
                                          .orElseThrow(() -> new NotFoundException("Không tìm thấy ghế"));

                Optional<Ticket> optionalTicket = ticketRepository.findByShowtimeAndSeat(showtime, seat);
                if (optionalTicket.isPresent()) {
                    throw new BadRequestException("Ghế đã được đặt");
                }

                // Check a couple seat
                if (tickets.stream().noneMatch(ticket -> ticket.getSeat().getName().equals(seat.getName())
                        && ticket.getSeat().isSeat() && seat.isSeat()
                        && ticket.getSeat().getStyle().equals(seat.getStyle()))) {
                    List<TicketPrice> ticketPrices = showtime.getRoom().getTheater().getTicketPrices();
                    TicketPrice ticketPrice = ticketPrices.stream()
                                                          .filter(tp -> tp.getSeatStyle().equals(seat.getStyle()))
                                                          .findFirst()
                                                          .orElseThrow(() -> new NotFoundException("Không tìm thấy " +
                                                                  "giá " +
                                                                  "vé"));

                    Ticket ticket = Ticket.builder()
                                          .showtime(showtime)
                                          .seat(seat)
                                          .ticketPrice(ticketPrice)
                                          .build();
                    tickets.add(ticket);
                }

                showtime.addBookedSeat(seat);
            }

            // Save tickets
            ticketRepository.saveAllAndFlush(tickets);

            // Update booked seats
            showtimeRepository.saveAndFlush(showtime);

            Invoice newInvoice = Invoice.builder()
                                        .code(request.getCode())
                                        .total(request.getTotal())
                                        .user(appUser)
                                        .paid(false)
                                        .build();
            newInvoice.addTickets(tickets);

            // Create invoice
            newInvoice = invoiceRepository.save(newInvoice);

            // create a payment link
            return createPaymentLink(newInvoice);
        } catch (NotFoundException | BadRequestException | IOException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceUnavailableException("Lỗi khi tạo link thanh toán");
        }
    }

    private LinkCreationResponse createPaymentLink(Invoice invoice) throws IOException, BaseException {
        try {
            int orderCode = invoice.getCode();
            int amount = invoice.getTotal();
            String description = "" + invoice.getCode();
            String cancelUrl = PAYOS_CANCEL_URL;
            String returnUrl = PAYOS_RETURN_URL;
            String signature = PayOSUtil.generateSignature(amount, cancelUrl, description, orderCode, returnUrl,
                    PAYOS_CHECKSUM_KEY);

            JsonObject json = new JsonObject();
            json.addProperty("orderCode", orderCode);
            json.addProperty("amount", amount);
            json.addProperty("description", description);
            json.addProperty("cancelUrl", cancelUrl);
            json.addProperty("returnUrl", returnUrl);
            json.addProperty("signature", signature);

            String response = Request.post(PAYOS_API_URL + "/v2/payment-requests")
                                     .addHeader("Content-Type", "application/json")
                                     .addHeader("x-client-id", PAYOS_CLIENT_ID)
                                     .addHeader("x-api-key", PAYOS_API_KEY)
                                     .bodyString(json.toString(),
                                             ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                                     .execute().returnContent().asString(StandardCharsets.UTF_8);

            Gson gson = new GsonBuilder().create();

            Type type = new TypeToken<PayOSResponse<LinkCreationResponse>>() {
            }.getType();
            PayOSResponse<LinkCreationResponse> payOSResponse = gson.fromJson(response, type);

            if (payOSResponse == null) {
                throw new BadRequestException("Tạo link thanh toán thất bại");
            }

            if (!Objects.equals(payOSResponse.getCode(), "00")) {
                throw new BadRequestException(payOSResponse.getDesc());
            }

            return payOSResponse.getData();
        } catch (IOException | BadRequestException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceUnavailableException("Lỗi khi tạo link thanh toán");
        }
    }

    @Override
    public InvoiceDTO cancelBooking(int code) throws BaseException {
        Invoice invoice = invoiceRepository.findByCode(code)
                                           .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        if (invoice.isPaid()) {
            throw new BadRequestException("Hóa đơn đã thanh toán");
        }

        List<Ticket> tickets = invoice.getTickets();
        for (Ticket ticket : tickets) {
            Showtime showtime = ticket.getShowtime();
            Seat seat = ticket.getSeat();
            showtime.removeBookedSeat(seat);
        }

        showtimeRepository.saveAllAndFlush(tickets.stream()
                                                  .map(Ticket::getShowtime)
                                                  .toList());

        invoice.setCanceled(true);
        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.toInvoiceDTO(invoice);
    }

    @Override
    public InvoiceDTO completePayment(int code) throws BaseException {
        Invoice invoice = invoiceRepository.findByCode(code)
                                           .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        if (invoice.isPaid()) {
            throw new BadRequestException("Hóa đơn đã thanh toán");
        }

        invoice.setPaid(true);
        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.toInvoiceDTO(invoice);
    }
}
