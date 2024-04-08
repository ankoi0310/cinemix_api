package vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotBlank
    String email;
    @NotBlank
    String OTP;
    @NotBlank
    String newPassword;
}
