package vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.config.mail.AppMailSender;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BadRequestException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.MailConstant;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final AppMailSender mailSender;

    @Override
    public void sendOTP(String email, String otp, OTPType type) throws BadRequestException {
        String from = MailConstant.NOREPLY_ADDRESS;
        String name = MailConstant.NOREPLY_NAME;
        String subject = MailConstant.VERIFICATION_SUBJECT;
        String content = "Mã OTP của bạn là: " + otp;
        try {
            mailSender.sendMail(from, name, email, content, subject, true);
        } catch (Exception e) {
            throw new BadRequestException("Không thể gửi mã OTP");
        }
    }
}
