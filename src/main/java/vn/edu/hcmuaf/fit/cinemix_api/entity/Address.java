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
@Table(name = "address")
public class Address extends BaseEntity {
    private String detail;
    private Long wardId;
    private Long districtId;
    private Long provinceId;
    private String fullAddress;

    @OneToOne(mappedBy = "address")
    @PrimaryKeyJoinColumn
    private Theater theater;
}
