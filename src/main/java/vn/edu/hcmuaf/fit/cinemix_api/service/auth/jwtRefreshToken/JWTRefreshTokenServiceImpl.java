package vn.edu.hcmuaf.fit.cinemix_api.service.auth.jwtRefreshToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.TokenException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.JWTRefreshToken;
import vn.edu.hcmuaf.fit.cinemix_api.repository.jwtRefreshToken.JWTRefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class JWTRefreshTokenServiceImpl implements JWTRefreshTokenService {

    private final JWTRefreshTokenRepository jwtRefreshTokenRepository;

    @Override
    public JWTRefreshToken createOrUpdate(AppUser appUser) {
        JWTRefreshToken jwtRefreshToken = JWTRefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(appUser)
                .expiredDate(LocalDateTime.now().plusDays(AppConstant.JWT_REFRESH_TOKEN_EXPIRED_DATE))
                .build();

        JWTRefreshToken availableRefreshToken = findByUser(appUser.getId()).orElse(jwtRefreshToken);

        if(availableRefreshToken.getId() != null)
        {
            availableRefreshToken.setToken(UUID.randomUUID().toString());
            availableRefreshToken.setExpiredDate(LocalDateTime.now().plusDays(AppConstant.JWT_REFRESH_TOKEN_EXPIRED_DATE));
        }

        return jwtRefreshTokenRepository.save(availableRefreshToken);
    }
    public JWTRefreshToken verifyExpiration(JWTRefreshToken token) {
        if (token.isExpiredToken()) {
            jwtRefreshTokenRepository.delete(token);
            throw new TokenException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public JWTRefreshToken findByToken(String token) {
        return jwtRefreshTokenRepository.findByToken(token)
                .map(this::verifyExpiration)
                .orElseThrow( () -> new TokenException(token, "Token not found"));
    }

    @Override
    public Optional<JWTRefreshToken> findByUser(Long userId) {
        return jwtRefreshTokenRepository.findByUser(userId);
    }
}
