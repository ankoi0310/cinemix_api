package vn.edu.hcmuaf.fit.cinemix_api.service.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.MovieState;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.*;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Genre;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Movie;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.MovieMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.genre.GenreRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.movie.MovieRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDTO> searchMovies(MovieSearch movieSearch) throws BaseException {
        try {
            List<Movie> movies = movieRepository.search(movieSearch);
            return movieMapper.toDTOs(movies);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Tìm kiếm phim không thành công");
        }
    }

    @Override
    public List<MovieDTO> getAllMovies() throws BaseException {
        try {
            List<Movie> movies = movieRepository.findAll();
            return movieMapper.toDTOs(movies);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Lấy danh sách phim không thành công");
        }
    }

    @Override
    public MovieDTO getMovieById(Long id) throws BaseException {
        try {
            Movie movie = movieRepository.findById(id)
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy phim theo id: " + id));
            return movieMapper.toDTO(movie);
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Lấy thông tin phim không thành công");
        }
    }

    @Override
    public MovieDTO createMovie(MovieCreate movieCreate) throws BaseException {
        try {
            Movie movie = movieRepository.findByTitle(movieCreate.getTitle()).orElse(null);
            if (movie != null) {
                throw new BadRequestException("Tên phim đã tồn tại: " + movieCreate.getTitle());
            }

            // Get all genres by id
            List<Long> genreIds = movieCreate.getGenres().stream().map(GenreDTO::getId).toList();
            List<Genre> genres = genreRepository.findAllById(genreIds);

            Movie newMovie = Movie.builder().title(movieCreate.getTitle()).genres(genres)
                                  .synopsis(movieCreate.getSynopsis()).description(movieCreate.getDescription())
                                  .director(movieCreate.getDirector()).actors(movieCreate.getActors())
                                  .duration(movieCreate.getDuration()).releaseDate(movieCreate.getReleaseDate())
//                                  .posterUrl(movieCreate.getPosterUrl())
                                  .trailerUrl(movieCreate.getTrailerUrl()).state(MovieState.COMING_SOON).build();

            movieRepository.save(newMovie);
            return movieMapper.toDTO(newMovie);
        } catch (BadRequestException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Tạo phim không thành công");
        }
    }

    @Override
    public MovieDTO updateMovie(MovieUpdate movieUpdate) throws BaseException {
        try {
            Movie movie = movieRepository.findById(movieUpdate.getId())
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy phim theo id: " + movieUpdate.getId()));

            Movie movieByTitle = movieRepository.findByTitle(movieUpdate.getTitle()).orElse(null);

            if (movieByTitle != null && !movieByTitle.getId().equals(movieUpdate.getId())) {
                throw new ServiceBusinessException("Tên phim đã tồn tại: " + movieUpdate.getTitle());
            }

            // Get all genres by id
            List<Long> genreIds = movieUpdate.getGenres().stream().map(GenreDTO::getId).toList();
            List<Genre> genres = genreRepository.findAllById(genreIds);

            movie.setTitle(movieUpdate.getTitle());
            movie.setGenres(genres);
            movie.setSynopsis(movieUpdate.getSynopsis());
            movie.setDescription(movieUpdate.getDescription());
            movie.setDirector(movieUpdate.getDirector());
            movie.setActors(movieUpdate.getActors());
            movie.setDuration(movieUpdate.getDuration());
            movie.setReleaseDate(movieUpdate.getReleaseDate());
//            movie.setPosterUrl(movieUpdate.getPosterUrl());
            movie.setTrailerUrl(movieUpdate.getTrailerUrl());

            movieRepository.save(movie);
            return movieMapper.toDTO(movie);
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Cập nhật phim không thành công");
        }
    }
}
