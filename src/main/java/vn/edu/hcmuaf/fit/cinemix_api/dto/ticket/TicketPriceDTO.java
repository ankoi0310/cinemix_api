package vn.edu.hcmuaf.fit.cinemix_api.dto.ticket;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.SeatStyle;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TicketPriceDTO extends BaseDTO {
    private String name;
    private SeatStyle seatStyle;
    private int price;
    private boolean special;
}
