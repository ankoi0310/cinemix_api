package vn.edu.hcmuaf.fit.cinemix_api.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.SeatStyle;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_price")
public class TicketPrice extends BaseEntity {
    private String name;

    @Convert(converter = SeatStyle.Converter.class)
    private SeatStyle seatStyle;

    private int price;

    private boolean special;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ticketPrices")
    private List<Theater> theaters;
}
