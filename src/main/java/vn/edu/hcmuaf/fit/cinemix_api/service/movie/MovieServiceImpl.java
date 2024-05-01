package vn.edu.hcmuaf.fit.cinemix_api.service.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie.MovieState;
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
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDTO> searchMovies(MovieSearch movieSearch) throws BaseException {
        try {
            List<Movie> movies = movieRepository.search(movieSearch);
            return movieMapper.toMovieDTOs(movies);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Tìm kiếm phim không thành công");
        }
    }

    @Override
    public List<MovieDTO> getAllMovies() throws BaseException {
        try {
            List<Movie> movies = movieRepository.findAll();
            return movieMapper.toMovieDTOs(movies);
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
            return movieMapper.toMovieDTO(movie);
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
            Movie movie = movieRepository.findByName(movieCreate.getName()).orElse(null);
            if (movie != null) {
                throw new BadRequestException("Tên phim đã tồn tại: " + movieCreate.getName());
            }

            // Get all genres by id
            List<Long> genreIds = movieCreate.getGenres().stream().map(GenreDTO::getId).toList();
            List<Genre> genres = genreRepository.findAllById(genreIds);

            Movie newMovie = Movie.builder()
                                  .name(movieCreate.getName())
                                  .genres(genres)
                                  .description(movieCreate.getDescription())
                                  .directors(movieCreate.getDirectors())
                                  .actors(movieCreate.getActors())
                                  .duration(movieCreate.getDuration())
                                  .releasedDate(movieCreate.getReleasedDate())
//                                  .posterUrl(movieCreate.getPosterUrl())
                                  .trailerUrl(movieCreate.getTrailerUrl())
                                  .state(movieCreate.getState()).build();

            movieRepository.save(newMovie);
            return movieMapper.toMovieDTO(newMovie);
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

            Movie movieByTitle = movieRepository.findByName(movieUpdate.getName()).orElse(null);

            if (movieByTitle != null && !movieByTitle.getId().equals(movieUpdate.getId())) {
                throw new BadRequestException("Tên phim đã tồn tại: " + movieUpdate.getName());
            }

            // Get all genres by id
            List<Long> genreIds = movieUpdate.getGenres().stream().map(GenreDTO::getId).toList();
            List<Genre> genres = genreRepository.findAllById(genreIds);

            movie.setName(movieUpdate.getName());
            movie.setGenres(genres);
            movie.setDescription(movieUpdate.getDescription());
            movie.setDirectors(movieUpdate.getDirectors());
            movie.setActors(movieUpdate.getActors());
            movie.setDuration(movieUpdate.getDuration());
            movie.setReleasedDate(movieUpdate.getReleasedDate());
//            movie.setPosterUrl(movieUpdate.getPosterUrl());
            movie.setTrailerUrl(movieUpdate.getTrailerUrl());

            movieRepository.save(movie);
            return movieMapper.toMovieDTO(movie);
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Cập nhật phim không thành công");
        }
    }

    @Override
    public MovieDTO updateMovieState(Long id, MovieState state) throws BaseException {
        try {
            Movie movie = movieRepository.findById(id)
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy phim theo id: " + id));

            movie.setState(state);
            movieRepository.save(movie);
            return movieMapper.toMovieDTO(movie);
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Cập nhật trạng thái phim không thành công");
        }
    }
}
