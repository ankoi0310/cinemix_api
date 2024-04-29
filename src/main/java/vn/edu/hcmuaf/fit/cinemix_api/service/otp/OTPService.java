package vn.edu.hcmuaf.fit.cinemix_api.service.otp;

import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.entity.OTP;

public interface OTPService {
    String generateOTP(String email) throws BaseException;

    OTP getOTPByEmail(String email) throws BaseException;

    OTP getOTPByCode(String code) throws BaseException;

    void verifyOTP(String code) throws BaseException;

    void deleteOTP(String code) throws BaseException;
}
