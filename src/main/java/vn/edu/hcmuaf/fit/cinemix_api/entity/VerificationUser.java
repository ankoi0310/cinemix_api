package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_user")
public class VerificationUser extends BaseEntity {
    @OneToOne(cascade=CascadeType.ALL, targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id",referencedColumnName = "id")
    private AppUser user;

    private String OTP;

    private LocalDateTime expiredDate;

    private int failedAttempts = 0;

    public boolean isExpired()
    {
        return expiredDate.isBefore(LocalDateTime.now());
    }
}
