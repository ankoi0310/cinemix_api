package vn.edu.hcmuaf.fit.cinemix_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank
    String otpCode;

    @NotBlank
    String newPassword;
}
