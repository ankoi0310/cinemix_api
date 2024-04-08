package vn.edu.hcmuaf.fit.cinemix_api.service.mail;

import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetOTP;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationUser;

public interface MailService {
    public void sendVerifyEmail(VerificationUser verificationUser) throws Exception;
    public void sendResetPasswordEmail(PasswordResetOTP passwordResetOTP) throws Exception;

}
