package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.UserRole;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_role")
public class AppRole extends BaseEntity {
    @Column(name = "name", unique = true)
    @Convert(converter = UserRole.Converter.class)
    private UserRole role;

    @OneToMany(mappedBy = "appRole")
    private List<AppUser> appUsers;
}
