package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.cinemix_api.service.showtime.ShowtimeService;

import java.util.List;

@RestController
@RequestMapping("/showtime")
@RequiredArgsConstructor
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @GetMapping
    public ResponseEntity<HttpResponse> searchShowtimes(@ModelAttribute ShowtimeSearch showtimeSearch) {
        List<ShowtimeDTO> showtimes = showtimeService.search(showtimeSearch);
        return ResponseEntity.ok(HttpResponse.success(showtimes, "Lấy danh sách lịch chiếu thành công"));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<HttpResponse> getShowtimesByMovieId(@PathVariable Long id) throws BaseException {
        List<ShowtimeDTO> showtimes = showtimeService.getShowtimesByMovieId(id);
        return ResponseEntity.ok(HttpResponse.success(showtimes, "Lấy thông tin lịch chiếu thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getShowtimeById(@PathVariable Long id) throws BaseException {
        ShowtimeDTO showtime = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(HttpResponse.success(showtime, "Lấy thông tin lịch chiếu thành công"));
    }
}
