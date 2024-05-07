package vn.edu.hcmuaf.fit.cinemix_api.dto.showtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.MovieDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.seat.SeatDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShowtimeDTO extends BaseDTO {
    @JsonIgnoreProperties("showtimes")
    private MovieDTO movie;

    private RoomDTO room;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime endTime;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate date;

    private List<SeatDTO> bookedSeats;
}
