package vn.edu.hcmuaf.fit.cinemix_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.MovieState;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.*;
import vn.edu.hcmuaf.fit.cinemix_api.service.movie.MovieService;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> searchMovies(@Valid MovieSearch movieSearch) throws BaseException {
        List<MovieDTO> movies = movieService.searchMovies(movieSearch);
        return ResponseEntity.ok(HttpResponse.success(movies, "Tìm kiếm phim thành công!"));
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getAllMovies() throws BaseException {
        List<MovieDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(HttpResponse.success(movies, "Lấy danh sách phim thành công!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getMovieById(@PathVariable Long id) throws BaseException {
        MovieDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(HttpResponse.success(movie, "Lấy phim thành công!"));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> createMovie(@Valid @ModelAttribute MovieCreate movieCreate,
                                                    BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            log.error("Không thể tạo phim: " + bindingResult.getAllErrors().getFirst().getDefaultMessage());
            return ResponseEntity.ok(HttpResponse.fail(bindingResult.getAllErrors().getFirst().getDefaultMessage()));
        }

        MovieDTO movie = movieService.createMovie(movieCreate);
        return ResponseEntity.ok(HttpResponse.success(movie, "Tạo phim thành công!"));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> updateMovie(@Valid @ModelAttribute MovieUpdate movieUpdate,
                                                    BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            if (log.isDebugEnabled()) {
                log.debug("Không thể cập nhật phim: " + bindingResult.getAllErrors().getFirst().getDefaultMessage());
            }

            log.error("Không thể cập nhật phim: " + bindingResult.getAllErrors().getFirst().getDefaultMessage());
            return ResponseEntity.ok(HttpResponse.fail(bindingResult.getAllErrors().getFirst().getDefaultMessage()));
        }

        MovieDTO movie = movieService.updateMovie(movieUpdate);
        return ResponseEntity.ok(HttpResponse.success(movie, "Cập nhật phim thành công!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateMovieState(@PathVariable Long id, @RequestParam MovieState state) throws BaseException {
        MovieDTO movie = movieService.updateMovieState(id, state);
        return ResponseEntity.ok(HttpResponse.success(movie, "Cập nhật trạng thái phim thành công!"));
    }
}
