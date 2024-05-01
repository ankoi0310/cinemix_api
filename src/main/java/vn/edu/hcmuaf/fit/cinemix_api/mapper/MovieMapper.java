package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.MovieDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Movie;

import java.util.List;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MovieMapper {
    @Named("toMovieDTO")
    MovieDTO toMovieDTO(Movie movie);

    @Named("toMovieEntity")
    Movie toMovieEntity(MovieDTO movieDTO);

    @IterableMapping(qualifiedByName = "toMovieDTO")
    List<MovieDTO> toMovieDTOs(List<Movie> movies);

    @IterableMapping(qualifiedByName = "toMovieEntity")
    List<Movie> toMovieEntities(List<MovieDTO> movieDTOs);
}
