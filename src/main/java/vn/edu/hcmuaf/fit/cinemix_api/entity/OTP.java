package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPState;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class OTP extends BaseEntity {
    private String code;
    private String email;
//    private String phone;

    @Enumerated(EnumType.STRING)
    private OTPType type;

    @Enumerated(EnumType.STRING)
    private OTPState state;

    private LocalDateTime sentAt;

    private LocalDateTime verifiedAt;

    private LocalDateTime expiredAt;
}
