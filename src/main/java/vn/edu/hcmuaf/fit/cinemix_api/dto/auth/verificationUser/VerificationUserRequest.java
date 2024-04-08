package vn.edu.hcmuaf.fit.cinemix_api.dto.auth.verificationUser;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;

@Data
public class VerificationUserRequest {
    @NotBlank
    private String email;
    @NotBlank
    @Length(max = AppConstant.OTP_LENGTH)
    private String OTP;
}
