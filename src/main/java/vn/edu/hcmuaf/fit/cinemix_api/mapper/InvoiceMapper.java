package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Invoice;

import java.util.List;

@Mapper(
        uses = {TicketMapper.class}
)
public interface InvoiceMapper {
    @Named("toInvoiceDTO")
    @Mapping(target = "tickets", qualifiedByName = "toTicketDTOs")
    InvoiceDTO toInvoiceDTO(Invoice invoice);

    @Named("toInvoiceDTOs")
    @IterableMapping(qualifiedByName = "toInvoiceDTO")
    List<InvoiceDTO> toInvoiceDTOs(List<Invoice> invoices);
}
