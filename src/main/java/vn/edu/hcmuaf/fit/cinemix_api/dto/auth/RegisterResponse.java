package vn.edu.hcmuaf.fit.cinemix_api.dto.auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String username;
}
