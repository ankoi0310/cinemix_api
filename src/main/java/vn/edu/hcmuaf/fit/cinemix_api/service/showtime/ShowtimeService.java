package vn.edu.hcmuaf.fit.cinemix_api.service.showtime;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeSearch;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeService {
    List<ShowtimeDTO> getAllShowtimes();

    List<ShowtimeDTO> search(ShowtimeSearch showtimeSearch);

    List<ShowtimeDTO> getShowtimesByMovieId(Long id) throws BaseException;

    List<ShowtimeDTO> getShowtimesByDate(LocalDate date);

    ShowtimeDTO getShowtimeById(Long id) throws BaseException;

    ShowtimeDTO createShowtime(ShowtimeDTO showtimeDTO) throws BaseException;

    ShowtimeDTO updateShowtime(ShowtimeDTO showtimeDTO) throws BaseException;

    void deleteShowtime(Long id) throws BaseException;
}
