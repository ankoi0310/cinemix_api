package vn.edu.hcmuaf.fit.cinemix_api.repository.verificationtoken;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationToken;

import java.util.Optional;

@NoRepositoryBean
public interface VerificationTokenRepository extends CustomRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByUser(Long id);
}
