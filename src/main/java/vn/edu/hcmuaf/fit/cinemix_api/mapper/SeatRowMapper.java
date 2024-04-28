package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.seat.SeatRowDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.SeatRow;

import java.util.List;

@Mapper(
        uses = {SeatMapper.class}
)
public interface SeatRowMapper {
    @Named("toSeatRowDTO")
    @Mapping(target = "seats", qualifiedByName = "toSeatDTOs")
    SeatRowDTO toSeatRowDTO(SeatRow seatRow);

    @Named("toSeatRowDTOs")
    @IterableMapping(qualifiedByName = "toSeatRowDTO")
    List<SeatRowDTO> toSeatRowDTOs(List<SeatRow> seatRows);
}
