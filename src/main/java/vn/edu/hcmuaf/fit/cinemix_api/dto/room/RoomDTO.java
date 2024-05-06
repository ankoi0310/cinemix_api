package vn.edu.hcmuaf.fit.cinemix_api.dto.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.seat.SeatRowDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.theater.TheaterDTO;

import java.util.List;

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

    @JsonIgnoreProperties({"rooms"})
    private TheaterDTO theater;

    private List<SeatRowDTO> rows;
}
