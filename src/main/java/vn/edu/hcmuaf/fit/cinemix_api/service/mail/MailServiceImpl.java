package vn.edu.hcmuaf.fit.cinemix_api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.config.mail.AppMailSender;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetOTP;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationUser;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService{
    private final AppMailSender mailSender;

    public void sendVerifyEmail(VerificationUser verificationUser) throws Exception
    {
        String content = "Dear " + verificationUser.getUser().getUserInfo().getFullName() +",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\""+ verificationUser.getOTP() +"\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Cinemix";
        String from ="noreply@cinemix.com";
        String subject = "Verify Email";

        mailSender.sendMail(from, verificationUser.getUser().getEmail(),content.toString(),subject,true);
    }
    public void sendResetPasswordEmail(PasswordResetOTP passwordResetOTP) throws Exception
    {

        String content = "Dear " + passwordResetOTP.getUser().getUserInfo().getFullName() +",<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\""+ passwordResetOTP.getOTP() +"\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Cinemix";
        String from ="noreply@cinemix.com";
        String subject = "Reset Password Email";

        mailSender.sendMail(from, passwordResetOTP.getUser().getEmail(),content.toString(),subject,true);
    }
}
