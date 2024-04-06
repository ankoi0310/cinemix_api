package vn.edu.hcmuaf.fit.cinemix_api.dto.auth.ForgotPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @NotBlank
    String email;
}
