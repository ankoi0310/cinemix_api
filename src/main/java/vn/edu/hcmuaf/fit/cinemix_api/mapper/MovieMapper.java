package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.MovieDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Movie;

import java.util.List;

@Mapper(
        uses = {ShowtimeMapper.class}
)
public interface MovieMapper {
    @Named("toMovieDTO")
    @Mapping(target = "format", source = "format.value")
    MovieDTO toMovieDTO(Movie movie);

    @IterableMapping(qualifiedByName = "toMovieDTO")
    List<MovieDTO> toMovieDTOs(List<Movie> movies);
}
