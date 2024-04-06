package vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.SecurityConstant;

@Data
@Builder
public class JWTRefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private String tokenType = SecurityConstant.TOKEN_PREFIX;
}