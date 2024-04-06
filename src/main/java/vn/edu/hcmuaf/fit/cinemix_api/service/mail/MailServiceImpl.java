package vn.edu.hcmuaf.fit.cinemix_api.service.mail;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.config.mail.AppMailSender;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetToken;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationToken;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService{
    private final AppMailSender mailSender;

    public void sendVerifyEmail(HttpServletRequest httpRequest, VerificationToken verificationToken) throws Exception
    {
        String siteUrl = httpRequest.getRequestURL().toString();
        siteUrl.replace(httpRequest.getRequestURI(),"");
        URIBuilder uriBuilder = new URIBuilder(siteUrl);
        uriBuilder.setPort(httpRequest.getServerPort());
        uriBuilder.setPath(httpRequest.getContextPath()+"/auth/verifyToken");
        uriBuilder.addParameter("token",verificationToken.getToken());
        System.out.println(uriBuilder.toString());
        String content = "Dear " + verificationToken.getUser().getUserInfo().getFullName() +",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\""+uriBuilder.toString() +"\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Cinemix";
        String from ="noreply@cinemix.com";
        String subject = "Verify Email";

        mailSender.sendMail(from,verificationToken.getUser().getEmail(),content.toString(),subject,true);
    }
    public void sendResetPasswordEmail(HttpServletRequest httpRequest, PasswordResetToken passwordResetToken) throws Exception
    {
        String siteUrl = httpRequest.getRequestURL().toString();
        siteUrl.replace(httpRequest.getRequestURI(),"");
        URIBuilder uriBuilder = new URIBuilder(siteUrl);
        uriBuilder.setPort(httpRequest.getServerPort());
        uriBuilder.setPath(httpRequest.getContextPath()+"/auth/resetPassword");
        uriBuilder.addParameter("token",passwordResetToken.getToken());
        System.out.println(uriBuilder.toString());
        String content = "Dear " + passwordResetToken.getUser().getUserInfo().getFullName() +",<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\""+uriBuilder.toString() +"\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Cinemix";
        String from ="noreply@cinemix.com";
        String subject = "Reset Password Email";

        mailSender.sendMail(from,passwordResetToken.getUser().getEmail(),content.toString(),subject,true);
    }
}
