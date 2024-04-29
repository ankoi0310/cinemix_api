package vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.mail;

import org.springframework.scheduling.annotation.Async;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BadRequestException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;

public interface MailService {

    @Async
    void sendOTP(String email, String otp, OTPType type) throws BadRequestException;

}
