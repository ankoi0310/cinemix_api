package vn.edu.hcmuaf.fit.cinemix_api.dto.theater;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.cinemix_api.dto.ticket.TicketPriceDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TheaterDTO extends BaseDTO {
    private String name;
    private String address;
    private String hotline;
    private String image;
    private String state;

    @JsonIgnoreProperties({"theater"})
    private List<RoomDTO> rooms;

    private List<TicketPriceDTO> ticketPrices;
}
