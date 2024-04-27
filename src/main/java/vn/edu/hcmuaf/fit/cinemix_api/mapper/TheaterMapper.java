package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.theater.TheaterDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Theater;

import java.util.List;

@Mapper
public interface TheaterMapper {
    @Named("toTheaterDTO")
    @Mapping(target = "address", source = "address.fullAddress")
    @Mapping(target = "state", source = "state.description")
    TheaterDTO toTheaterDTO(Theater theater);

    @IterableMapping(qualifiedByName = "toTheaterDTO")
    List<TheaterDTO> toTheaterDTOs(List<Theater> theaters);
}
