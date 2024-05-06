package vn.edu.hcmuaf.fit.cinemix_api.dto.showtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShowtimeSearch {
    private Long movieId;
    private Long theaterId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
