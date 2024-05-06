package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {
    private String fullName;
    private Boolean gender;

    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    private String avatar;

    @OneToOne(mappedBy = "userInfo")
    private AppUser appUser;
}
