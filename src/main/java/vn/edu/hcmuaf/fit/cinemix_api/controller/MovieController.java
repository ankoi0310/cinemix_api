package vn.edu.hcmuaf.fit.cinemix_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.*;
import vn.edu.hcmuaf.fit.cinemix_api.service.movie.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllMovies() {
        List<MovieDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(HttpResponse.success(movies, "Get all movies successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getMovieById(@PathVariable Long id) {
        try {
            MovieDTO movie = movieService.getMovieById(id);
            return ResponseEntity.ok(HttpResponse.success(movie, "Get movie by id successfully"));
        } catch (Exception e) {
            logger.error("Error getting movie by id", e);
            return ResponseEntity.ok(HttpResponse.fail("Error getting movie by id"));
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> createMovie(@Valid @ModelAttribute MovieCreate movieCreate,
                                                    BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Không thể tạo phim: " + bindingResult.getAllErrors().getFirst().getDefaultMessage());
            }

            logger.error("Không thể tạo phim: " + bindingResult.getAllErrors().getFirst().getDefaultMessage());
            return ResponseEntity.ok(HttpResponse.fail(bindingResult.getAllErrors().getFirst().getDefaultMessage()));
        }

        MovieDTO movie = movieService.createMovie(movieCreate);
        return ResponseEntity.ok(HttpResponse.success(movie, "Tạo phim thành công!"));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> updateMovie(@Valid @ModelAttribute MovieUpdate movieUpdate,
                                                    BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Không thể cập nhật phim: " + bindingResult.getAllErrors().getFirst().getDefaultMessage());
            }

            logger.error("Không thể cập nhật phim: " + bindingResult.getAllErrors().getFirst().getDefaultMessage());
            return ResponseEntity.ok(HttpResponse.fail(bindingResult.getAllErrors().getFirst().getDefaultMessage()));
        }

        MovieDTO movie = movieService.updateMovie(movieUpdate);
        return ResponseEntity.ok(HttpResponse.success(movie, "Cập nhật phim thành công!"));
    }
}
