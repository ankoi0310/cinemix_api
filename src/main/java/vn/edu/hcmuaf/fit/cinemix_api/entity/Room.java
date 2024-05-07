package vn.edu.hcmuaf.fit.cinemix_api.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room extends BaseEntity {
    private String name;
    private int maxRow;
    private int maxColumn;
    private int seatCount;
    private boolean available;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<SeatRow> rows;
}
