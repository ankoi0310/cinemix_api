package vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JWTRefreshTokenRequest {
    @NotBlank
    private String token;
}
