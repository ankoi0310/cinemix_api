package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.movie.MovieDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Movie;

import java.util.List;

@Mapper
public interface MovieMapper {
    @Named("toDTO")
    @Mapping(target = "state", source = "state.value")
    MovieDTO toDTO(Movie movie);

    @Named("toEntity")
    Movie toEntity(MovieDTO movieDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<MovieDTO> toDTOs(List<Movie> movies);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Movie> toEntities(List<MovieDTO> movieDTOs);
}
