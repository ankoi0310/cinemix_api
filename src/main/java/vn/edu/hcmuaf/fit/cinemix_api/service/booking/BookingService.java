package vn.edu.hcmuaf.fit.cinemix_api.service.booking;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.payment.payos.LinkCreationResponse;

import java.io.IOException;

public interface BookingService {
    LinkCreationResponse createBooking(BookingRequest request) throws BaseException, IOException;

    void cancelBooking(int code) throws BaseException;

    InvoiceDTO successBooking(int code) throws BaseException;
}
