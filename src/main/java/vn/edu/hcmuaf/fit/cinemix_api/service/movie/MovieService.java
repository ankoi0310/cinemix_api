package vn.edu.hcmuaf.fit.cinemix_api.service.movie;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.*;

import java.util.List;

public interface MovieService {
    List<MovieDTO> searchMovies(MovieSearch movieSearch) throws BaseException;

    List<MovieDTO> getAllMovies() throws BaseException;

    MovieDTO getMovieById(Long id) throws BaseException;

    MovieDTO createMovie(MovieCreate movieCreate) throws BaseException;

    MovieDTO updateMovie(MovieUpdate movieUpdate) throws BaseException;
}
