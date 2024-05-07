package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.seat.SeatDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Seat;

import java.util.List;

@Mapper
public interface SeatMapper {
    @Named("toSeatDTO")
    @Mapping(target = "isSeat", source = "seat")
    SeatDTO toSeatDTO(Seat seat);

    @Named("toSeatDTOs")
    @IterableMapping(qualifiedByName = "toSeatDTO")
    List<SeatDTO> toSeatDTOs(List<Seat> seats);
}
