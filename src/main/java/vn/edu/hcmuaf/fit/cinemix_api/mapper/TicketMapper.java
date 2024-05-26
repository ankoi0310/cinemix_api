package vn.edu.hcmuaf.fit.cinemix_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.ticket.TicketDTO;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Ticket;

import java.util.List;

@Mapper(
        uses = {ShowtimeMapper.class, SeatMapper.class}
)
public interface TicketMapper {
    @Named("toTicketDTO")
    @Mapping(target = "showtime", qualifiedByName = "toShowtimeDTO")
    @Mapping(target = "seat", qualifiedByName = "toSeatDTO")
    @Mapping(target = "price", source = "ticketPrice.price")
    TicketDTO toTicketDTO(Ticket ticket);

    @Named("toTicketDTOs")
    @IterableMapping(qualifiedByName = "toTicketDTO")
    List<TicketDTO> toTicketDTOs(List<Ticket> tickets);
}
