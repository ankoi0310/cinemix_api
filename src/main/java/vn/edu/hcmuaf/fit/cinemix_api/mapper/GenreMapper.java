package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.GenreDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Genre;

import java.util.List;

@Mapper
public interface GenreMapper {
    @Named("toDTO")
    GenreDTO toDTO(Genre genre);

    @Named("toEntity")
    Genre toEntity(GenreDTO genreDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<GenreDTO> toDTOs(List<Genre> genres);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Genre> toEntities(List<GenreDTO> genreDTOs);
}
