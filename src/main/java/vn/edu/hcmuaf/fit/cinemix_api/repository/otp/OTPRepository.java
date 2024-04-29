package vn.edu.hcmuaf.fit.cinemix_api.repository.otp;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.OTP;

import java.util.Optional;

@NoRepositoryBean
public interface OTPRepository extends CustomRepository<OTP, Long> {
    Optional<OTP> findByCode(String code);

    Optional<OTP> findByEmail(String email);
}
