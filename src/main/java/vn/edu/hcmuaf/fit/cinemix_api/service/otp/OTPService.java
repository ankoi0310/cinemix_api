package vn.edu.hcmuaf.fit.cinemix_api.service.otp;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.cinemix_api.entity.OTP;

public interface OTPService {
    OTP generateOTP(String email, OTPType type) throws BaseException;

    OTP getOTPByEmail(String email) throws BaseException;

    OTP getOTPByCode(String code) throws BaseException;

    void verifyOTP(String email, String code) throws BaseException;

    void deleteOTP() throws BaseException;
}
