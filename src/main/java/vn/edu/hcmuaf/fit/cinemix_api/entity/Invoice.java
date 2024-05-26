package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity {
    private int code;

    private int total;

    @ManyToOne
    private AppUser user;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @Column(columnDefinition = "boolean default false")
    private boolean paid;

    @Column(columnDefinition = "boolean default false")
    private boolean canceled;

    public void addTickets(List<Ticket> tickets) {
        if (this.tickets == null) {
            this.tickets = new ArrayList<>();
        }

        this.tickets.addAll(tickets);
        tickets.forEach(ticket -> ticket.setInvoice(this));
    }
}
