package vn.edu.hcmuaf.fit.cinemix_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
