package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.theater.TheaterDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Room;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Theater;

import java.util.List;

@Mapper(
        uses = {SeatRowMapper.class}
)
public interface RoomMapper {
    @Named("toRoomDTO")
    @Mapping(target = "theater", qualifiedByName = "toTheaterDTOWithoutRooms")
    @Mapping(target = "rows", qualifiedByName = "toSeatRowDTOs")
    RoomDTO toRoomDTO(Room room);

    @Named("toRoomDTOs")
    @IterableMapping(qualifiedByName = "toRoomDTO")
    List<RoomDTO> toRoomDTOs(List<Room> rooms);

    @Named("toRoomDTOWithoutTheater")
    @Mapping(target = "theater", ignore = true)
    @Mapping(target = "rows", qualifiedByName = "toSeatRowDTOs")
    RoomDTO toRoomDTOWithoutTheater(Room room);

    @Named("toRoomDTOsWithoutTheater")
    @IterableMapping(qualifiedByName = "toRoomDTOWithoutTheater")
    List<RoomDTO> toRoomDTOsWithoutTheater(List<Room> rooms);

    @Named("toTheaterDTOWithoutRooms")
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "address", source = "address.fullAddress")
    TheaterDTO toTheaterDTOWithoutRooms(Theater theater);
}
