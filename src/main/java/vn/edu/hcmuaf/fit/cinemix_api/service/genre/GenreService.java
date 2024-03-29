package vn.edu.hcmuaf.fit.cinemix_api.service.genre;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.*;

import java.util.List;

public interface GenreService {
    List<GenreDTO> searchGenres(GenreSearch genreSearch);
    List<GenreDTO> getAllGenres();
    GenreDTO getGenreById(Long id) throws BaseException;
    GenreDTO createGenre(GenreCreate genreCreate) throws BaseException;
    GenreDTO updateGenre(GenreUpdate genreUpdate) throws BaseException;
//    GenreDTO updateStatusGenre(Long id, GenreState state) throws BaseException;
}
