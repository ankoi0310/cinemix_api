package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="password_reset_token")
@SuperBuilder
public class PasswordResetToken extends BaseEntity {
    @Column(unique = true)
    private String token;

    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    private LocalDateTime expiredDate;

    public boolean isTokenExpired() {
        return  expiredDate.isBefore(LocalDateTime.now());
    }
}
