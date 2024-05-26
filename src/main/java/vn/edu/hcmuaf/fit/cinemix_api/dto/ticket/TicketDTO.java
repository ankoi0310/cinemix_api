package vn.edu.hcmuaf.fit.cinemix_api.dto.ticket;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.seat.SeatDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TicketDTO extends BaseDTO {
    private ShowtimeDTO showtime;
    private SeatDTO seat;
    private int price;
}
