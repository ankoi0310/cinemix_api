package vn.edu.hcmuaf.fit.cinemix_api.dto.room;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomDTO extends BaseDTO {
    private String name;
    private int maxRow;
    private int maxColumn;
    private int seatCount;
    private boolean available;
}
