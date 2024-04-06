package vn.edu.hcmuaf.fit.cinemix_api.repository.jwtRefreshToken;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.cinemix_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.cinemix_api.entity.JWTRefreshToken;

import java.util.Optional;
@NoRepositoryBean
public interface JWTRefreshTokenRepository extends CustomRepository<JWTRefreshToken,Long> {
    Optional<JWTRefreshToken> findByToken(String token);

    Optional<JWTRefreshToken> findByUser(Long userId);

}
