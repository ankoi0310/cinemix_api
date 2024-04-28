package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Room;

import java.util.List;

@Mapper(
        uses = {SeatRowMapper.class}
)
public interface RoomMapper {
    @Named("toRoomDTO")
    @Mapping(target = "rows", qualifiedByName = "toSeatRowDTOs")
    RoomDTO toRoomDTO(Room room);

    @Named("toRoomDTOs")
    @IterableMapping(qualifiedByName = "toRoomDTO")
    List<RoomDTO> toRoomDTOs(List<Room> rooms);
}
