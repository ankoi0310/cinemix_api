package vn.edu.hcmuaf.fit.cinemix_api.dto.invoice;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.ticket.TicketDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvoiceDTO extends BaseDTO {
    private int code;
    private int total;
    private List<TicketDTO> tickets;
    private boolean paid;
    private boolean canceled;
}
