package vn.edu.hcmuaf.fit.cinemix_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.annotation.EmailOrPhone;

@Data
public class LoginRequest {
    @EmailOrPhone
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
