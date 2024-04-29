package vn.edu.hcmuaf.fit.cinemix_api.service.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.entity.OTP;
import vn.edu.hcmuaf.fit.cinemix_api.repository.otp.OTPRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {
    private final OTPRepository otpRepository;

    @Override
    public String generateOTP(String email) throws BaseException {
        return "";
    }

    @Override
    public OTP getOTPByEmail(String email) throws BaseException {
        return null;
    }

    @Override
    public OTP getOTPByCode(String code) throws BaseException {
        return null;
    }

    @Override
    public void verifyOTP(String code) throws BaseException {

    }

    @Override
    public void deleteOTP(String code) throws BaseException {

    }
}
