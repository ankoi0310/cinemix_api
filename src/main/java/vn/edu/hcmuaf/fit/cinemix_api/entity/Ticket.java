package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket extends BaseEntity {
    @ManyToOne
    private Showtime showtime;

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private TicketPrice ticketPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
