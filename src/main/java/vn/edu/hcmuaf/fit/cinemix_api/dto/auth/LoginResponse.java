package vn.edu.hcmuaf.fit.cinemix_api.dto.auth;

import lombok.Builder;
import lombok.Data;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.SecurityConstant;

@Data
@Builder
public class LoginResponse {
    private String email;
    private String fullname;
    private String birthday;
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private String tokenType = SecurityConstant.TOKEN_PREFIX;
}
