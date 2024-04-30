package vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cinemix_api.core.config.mail.AppMailSender;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.ServiceUnavailableException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final AppMailSender mailSender;

    @Value("${mail.address.noreply}")
    private String NOREPLY_ADDRESS;

    @Value("${mail.address.support}")
    private String SUPPORT_ADDRESS;

    private final String NOREPLY_NAME = "Cinemix";
    private final String SUPPORT_NAME = "Cinemix Support";
    private final String VERIFICATION_SUBJECT = "Cinemix - Xác thực tài khoản";
    private final String FORGOT_PASSWORD_SUBJECT = "Cinemix - Quên mật khẩu";

    @Async("sendOTP")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendOTP(String email, String code, OTPType type) throws BaseException {
        try {
            String subject = switch (type) {
                case REGISTER -> VERIFICATION_SUBJECT;
                case FORGOT_PASSWORD -> FORGOT_PASSWORD_SUBJECT;
            };
            String from = NOREPLY_ADDRESS;
            String name = NOREPLY_NAME;
            String content = "Mã OTP của bạn là: " + code;
            mailSender.sendMail(from, name, email, subject, content, true);
        } catch (Exception e) {
            throw new ServiceUnavailableException("Không thể gửi mã OTP");
        }
    }
}
