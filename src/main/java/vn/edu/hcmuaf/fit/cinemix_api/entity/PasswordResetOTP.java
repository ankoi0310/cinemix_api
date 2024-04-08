package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="password_reset_OTP")
@SuperBuilder
public class PasswordResetOTP extends BaseEntity {
    private String OTP;

    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    private LocalDateTime expiredDate;

    private int failedAttempts = 0;

    public boolean isExpired() {
        return  expiredDate.isBefore(LocalDateTime.now());
    }
}
