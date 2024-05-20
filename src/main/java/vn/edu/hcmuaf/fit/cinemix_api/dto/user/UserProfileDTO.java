package vn.edu.hcmuaf.fit.cinemix_api.dto.user;

import lombok.Builder;
import lombok.Data;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.UserRole;

@Data
@Builder
public class UserProfileDTO {
    private String email;
    private String fullName;
    private String phone;
    private boolean emailVerified;
    private boolean phoneVerified;
    private UserRole role;
}
