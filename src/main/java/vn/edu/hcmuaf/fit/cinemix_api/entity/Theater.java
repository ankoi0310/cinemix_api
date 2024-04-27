package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.TheaterState;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "theater")
public class Theater extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private String hotline;

    private String image;

    @Convert(converter = TheaterState.TheaterStateConverter.class)
    private TheaterState state;
}