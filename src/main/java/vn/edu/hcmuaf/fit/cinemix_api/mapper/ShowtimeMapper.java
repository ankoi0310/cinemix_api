package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Showtime;

import java.util.List;

@Mapper(
        uses = {MovieMapper.class, RoomMapper.class, SeatMapper.class}
)
public interface ShowtimeMapper {
    @Named("toShowtimeDTO")
    @Mapping(target = "movie", qualifiedByName = "toMovieDTO")
    @Mapping(target = "room", qualifiedByName = "toRoomDTO")
    @Mapping(target = "bookedSeats", source = "bookedSeats", qualifiedByName = "toSeatDTOs")
    ShowtimeDTO toShowtimeDTO(Showtime showtime);

    @Named("toShowtimeDTOs")
    @IterableMapping(qualifiedByName = "toShowtimeDTO")
    List<ShowtimeDTO> toShowtimeDTOs(List<Showtime> showtimes);
}
