package vn.edu.hcmuaf.fit.cinemix_api.dto.booking;

import lombok.Data;
import vn.edu.hcmuaf.fit.cinemix_api.dto.seat.SeatDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;

import java.util.List;

@Data
public class BookingRequest {
    private int code;
    private ShowtimeDTO showtime;
    private List<SeatDTO> seats;
    private int total;
}
