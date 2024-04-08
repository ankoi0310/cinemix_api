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
    @Column(unique = true)
    private String name;

    @Transient
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "appRole")
    private List<AppUser> appUsers;

    public AppRole(UserRole role) {
        this.role = role;
        this.name = role.name();
    }

    public UserRole getRole() {
        return UserRole.valueOf(name);
    }

    public void setRole(UserRole role) {
        this.role = role;
        this.name = role.name();
    }
}
