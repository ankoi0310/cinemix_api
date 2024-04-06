package vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ResetPasswordRequest {
    @NotBlank
    String token;
    @NotBlank
    String newPassword;
}
