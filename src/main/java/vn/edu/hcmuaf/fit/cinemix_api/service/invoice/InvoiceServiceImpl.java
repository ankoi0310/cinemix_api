package vn.edu.hcmuaf.fit.cinemix_api.service.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Invoice;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.InvoiceMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.invoice.InvoiceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoiceMapper.toInvoiceDTOs(invoices);
    }

    @Override
    public List<InvoiceDTO> getInvoicesByUser(AppUser user) {
        List<Invoice> invoices = invoiceRepository.findByUser(user);
        return invoiceMapper.toInvoiceDTOs(invoices);
    }

    @Override
    public InvoiceDTO getInvocieById(Long id) throws BaseException {
        Invoice invoice = invoiceRepository.findById(id)
                                           .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));
        return invoiceMapper.toInvoiceDTO(invoice);
    }
}
