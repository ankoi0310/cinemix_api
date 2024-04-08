package vn.edu.hcmuaf.fit.cinemix_api.repository.passwordResetOTP;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetOTP;

import java.util.Optional;

@NoRepositoryBean
public interface PasswordResetOTPRepository extends CustomRepository<PasswordResetOTP, Long> {
    Optional<PasswordResetOTP> findByOTP(String otp);
    Optional<PasswordResetOTP> findByUser(Long id);

    Optional<PasswordResetOTP> findByEmail(String email);

}
