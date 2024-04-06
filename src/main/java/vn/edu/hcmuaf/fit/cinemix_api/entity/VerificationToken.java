package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade=CascadeType.ALL, targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id",referencedColumnName = "id")
    private AppUser user;

    @Column(unique = true)
    private String token;

    private LocalDateTime expiredDate;

    public boolean isExpired()
    {
        return expiredDate.isBefore(LocalDateTime.now());
    }
}
