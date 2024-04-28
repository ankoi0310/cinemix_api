package vn.edu.hcmuaf.fit.cinemix_api.dto.seat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SeatDTO extends BaseDTO {
    private String name;
    private int columnIndex;
    private int seatIndex;

    @JsonProperty("isSeat")
    private boolean isSeat;

}
