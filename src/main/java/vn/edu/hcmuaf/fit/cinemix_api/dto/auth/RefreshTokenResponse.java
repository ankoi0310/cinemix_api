package vn.edu.hcmuaf.fit.cinemix_api.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
