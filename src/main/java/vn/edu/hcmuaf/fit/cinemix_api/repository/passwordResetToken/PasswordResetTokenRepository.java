package vn.edu.hcmuaf.fit.cinemix_api.repository.passwordResetToken;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetToken;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface PasswordResetTokenRepository extends CustomRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
