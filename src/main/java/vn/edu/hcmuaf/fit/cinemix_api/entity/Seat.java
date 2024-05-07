package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.SeatStyle;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat")
public class Seat extends BaseEntity {
    private String name;
    private int columnIndex;
    private int seatIndex;
    private boolean isSeat;

    @Convert(converter = SeatStyle.Converter.class)
    private SeatStyle style;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "row_id")
    private SeatRow row;
}
