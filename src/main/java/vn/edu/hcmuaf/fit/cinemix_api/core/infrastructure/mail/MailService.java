package vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.mail;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;

public interface MailService {

    void sendOTP(String email, String code, OTPType type) throws BaseException;

}
