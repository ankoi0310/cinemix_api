package vn.edu.hcmuaf.fit.cinemix_api.repository.verificationUser;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationUser;

import java.util.Optional;

@NoRepositoryBean
public interface VerificationUserRepository extends CustomRepository<VerificationUser, Long> {

    Optional<VerificationUser> findByOTP(String OTP);

    Optional<VerificationUser> findByUser(Long id);

    Optional<VerificationUser> findByEmail(String email);
}
