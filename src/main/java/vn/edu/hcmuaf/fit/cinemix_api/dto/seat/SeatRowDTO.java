package vn.edu.hcmuaf.fit.cinemix_api.dto.seat;


import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SeatRowDTO extends BaseDTO {
    private String name;
    private List<SeatDTO> seats;
}
