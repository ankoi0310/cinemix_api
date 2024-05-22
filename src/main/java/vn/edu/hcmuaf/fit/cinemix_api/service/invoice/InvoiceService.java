package vn.edu.hcmuaf.fit.cinemix_api.service.invoice;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getAllInvoices();

    List<InvoiceDTO> getInvoicesByUser(AppUser user);

    InvoiceDTO getInvocieById(Long id) throws BaseException;
}
