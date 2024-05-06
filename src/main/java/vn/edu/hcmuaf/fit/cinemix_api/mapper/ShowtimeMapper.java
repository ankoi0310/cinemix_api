package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.showtime.ShowtimeDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Showtime;

import java.util.List;

@Mapper(
        uses = {RoomMapper.class, SeatMapper.class}
)
public interface ShowtimeMapper {
    @Named("toShowtimeDTO")
    @Mapping(target = "movie.showtimes", ignore = true)
    @Mapping(target = "room", qualifiedByName = "toRoomDTO")
    @Mapping(target = "bookedSeats", source = "bookedSeats", qualifiedByName = "toSeatDTOs")
    ShowtimeDTO toShowtimeDTO(Showtime showtime);

    @Named("toShowtimeDTOs")
    @IterableMapping(qualifiedByName = "toShowtimeDTO")
    List<ShowtimeDTO> toShowtimeDTOs(List<Showtime> showtimes);

    @Named("toShowtimeDTOWithoutMovie")
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "room", qualifiedByName = "toRoomDTO")
    ShowtimeDTO toShowtimeDTOWithoutMovie(Showtime showtime);

    @Named("toShowtimeDTOsWithoutMovie")
    @IterableMapping(qualifiedByName = "toShowtimeDTOWithoutMovie")
    List<ShowtimeDTO> toShowtimeDTOsWithoutMovie(List<Showtime> showtimes);
}
