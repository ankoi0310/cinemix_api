package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name="jwt_refresh_token")
public class JWTRefreshToken extends BaseEntity {

    @OneToOne(cascade=CascadeType.ALL, targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id",referencedColumnName = "id")
    private AppUser user;

    @Column(unique = true)
    private String token;
    private LocalDateTime expiredDate;

    public boolean isExpiredToken()
    {
        return expiredDate.isBefore(LocalDateTime.now());
    }
}
