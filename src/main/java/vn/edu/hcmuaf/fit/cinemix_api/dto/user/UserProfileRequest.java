package vn.edu.hcmuaf.fit.cinemix_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.annotation.Phone;

@Data
public class UserProfileRequest {
    @NotNull
    @NotBlank
    private String fullName;

    @Phone
    private String phone;
}
