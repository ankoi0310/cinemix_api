package vn.edu.hcmuaf.fit.cinemix_api.dto.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
