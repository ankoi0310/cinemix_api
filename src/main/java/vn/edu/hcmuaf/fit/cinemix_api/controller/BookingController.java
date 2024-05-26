package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.payment.payos.LinkCreationResponse;
import vn.edu.hcmuaf.fit.cinemix_api.service.booking.BookingService;

import java.io.IOException;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/checkout/cancel")
    public ResponseEntity<HttpResponse> cancel(@RequestParam int code) throws BaseException {
        InvoiceDTO invoice = bookingService.cancelBooking(code);
        return ResponseEntity.ok(HttpResponse.success(invoice, "Hủy vé thành công!"));
    }

    @GetMapping("/checkout/complete")
    public ResponseEntity<HttpResponse> success(@RequestParam int code) throws BaseException {
        InvoiceDTO invoice = bookingService.completePayment(code);
        return ResponseEntity.ok(HttpResponse.success(invoice, "Thanh toán thành công!"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createBooking(@RequestBody BookingRequest request) throws BaseException,
            IOException {
        LinkCreationResponse linkCreationResponse = bookingService.createBooking(request);
        return ResponseEntity.ok(HttpResponse.success(linkCreationResponse, "Tạo vé thành công!"));
    }
}
