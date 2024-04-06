package vn.edu.hcmuaf.fit.cinemix_api.service.auth.jwtRefreshToken;

import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.JWTRefreshToken;

import java.util.Optional;

public interface JWTRefreshTokenService {
    public JWTRefreshToken createOrUpdate(AppUser appUser);

    public JWTRefreshToken findByToken(String token);

    public Optional<JWTRefreshToken> findByUser(Long userId);
}
