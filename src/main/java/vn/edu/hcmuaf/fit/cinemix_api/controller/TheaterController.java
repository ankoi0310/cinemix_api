package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.theater.TheaterDTO;
import vn.edu.hcmuaf.fit.cinemix_api.service.theater.TheaterService;

import java.util.List;

@RestController
@RequestMapping("/theater")
@RequiredArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllTheaters() {
        List<TheaterDTO> theaters = theaterService.getAllTheaters();
        return ResponseEntity.ok(HttpResponse.success(theaters, "Lấy danh sách rạp thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getTheaterById(@PathVariable Long id) throws BaseException {
        TheaterDTO theater = theaterService.getTheaterById(id);
        return ResponseEntity.ok(HttpResponse.success(theater, "Lấy thông tin rạp thành công"));
    }
}
