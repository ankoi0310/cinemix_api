package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.*;
import vn.edu.hcmuaf.fit.cinemix_api.service.genre.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();

        return ResponseEntity.ok(HttpResponse.success(genres, "Get all genres successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getGenreById(@PathVariable Long id) throws BaseException {
        GenreDTO genre = genreService.getGenreById(id);

        return ResponseEntity.ok(HttpResponse.success(genre, "Get genre by id successfully"));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<HttpResponse> searchGenres(GenreSearch genreSearch) {
        List<GenreDTO> genres = genreService.searchGenres(genreSearch);

        return ResponseEntity.ok(HttpResponse.success(genres, "Search genres successfully"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createGenre(@RequestBody GenreCreate genreCreate) throws BaseException {
        GenreDTO genre = genreService.createGenre(genreCreate);

        return ResponseEntity.ok(HttpResponse.success(genre, "Create genre successfully"));
    }

    @PutMapping
    public ResponseEntity<HttpResponse> updateGenre(@RequestBody GenreUpdate genreUpdate) throws BaseException {
        GenreDTO genre = genreService.updateGenre(genreUpdate);

        return ResponseEntity.ok(HttpResponse.success(genre, "Update genre successfully"));
    }
}
