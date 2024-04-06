package vn.edu.hcmuaf.fit.cinemix_api.service.mail;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetToken;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationToken;

public interface MailService {
    public void sendVerifyEmail(HttpServletRequest httpRequest, VerificationToken verificationToken) throws Exception;
    public void sendResetPasswordEmail(HttpServletRequest httpRequest, PasswordResetToken passwordResetToken) throws Exception;

}
