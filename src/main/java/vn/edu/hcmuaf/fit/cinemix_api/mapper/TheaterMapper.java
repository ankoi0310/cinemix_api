package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.theater.TheaterDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Theater;

import java.util.List;

@Mapper(
        uses = {RoomMapper.class}
)
public abstract class TheaterMapper {
    @Named("toTheaterDTO")
    @Mapping(target = "rooms", qualifiedByName = "toRoomDTOsWithoutTheater")
    @Mapping(target = "address", source = "address.fullAddress")
    public abstract TheaterDTO toTheaterDTO(Theater theater);

    @IterableMapping(qualifiedByName = "toTheaterDTO")
    public abstract List<TheaterDTO> toTheaterDTOs(List<Theater> theaters);
}
