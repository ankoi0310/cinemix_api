package vn.edu.hcmuaf.fit.cinemix_api.service.auth.passwordReset;

import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword.PasswordResetRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetOTP;

public interface PasswordResetOTPService {
    public PasswordResetOTP create(AppUser user);

    public PasswordResetOTP findByRequest(PasswordResetRequest request);

    public void delete(PasswordResetOTP passwordResetOTP);

    public void increaseFailedAttempts(String email);


}
