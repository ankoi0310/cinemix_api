package vn.edu.hcmuaf.fit.cinemix_api.service.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPState;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.cinemix_api.entity.OTP;
import vn.edu.hcmuaf.fit.cinemix_api.repository.otp.OTPRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {
    private final OTPRepository otpRepository;

    @Value("${otp.length}")
    private int length;

    @Value("${otp.expired-time}")
    private int expiredTime;

    @Value("${otp.failed-attempt}")
    private int failedAttempt;

    @Value("${otp.try-after}")
    private int tryAfter;

    @Value("${otp.resend-time}")
    private int resendTime;

    @Value("${otp.resend-limit}")
    private int resendLimit;

    @Override
    public OTP generateOTP(String email, OTPType type) throws BaseException {
        try {
            // Check if already exist OTP with email
            OTP otpExist = otpRepository.findByEmail(email).orElse(null);

            // if otp existed, check limit resend otp time
            if (otpExist != null) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime resendAt = otpExist.getSentAt().plusSeconds(resendTime);
                int remainingTime = resendTime - (int) now.until(resendAt, ChronoUnit.SECONDS);

                if (now.isBefore(resendAt)) {
                    throw new BadRequestException("Vui lòng đợi " + remainingTime + " giây trước khi gửi lại mã OTP");
                }

                if (otpExist.getResendCount() >= resendLimit) {
                    throw new TooManyRequestException("Đã vượt quá số lần gửi lại mã OTP");
                }

                // create new code and update resend count
                String code = RandomStringUtils.randomNumeric(length);
                otpExist.setCode(code);
                otpExist.setResendCount(otpExist.getResendCount() + 1);
                otpRepository.save(otpExist);
            }

            String code = RandomStringUtils.randomNumeric(length);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiredAt = now.plusSeconds(expiredTime);

            OTP otp = OTP.builder()
                         .code(code)
                         .email(email)
                         .type(type)
                         .state(OTPState.ACTIVE)
                         .sentAt(now)
                         .expiredAt(expiredAt)
                         .build();

            otpRepository.save(otp);

            return otp;
        } catch (BadRequestException | TooManyRequestException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error generate OTP: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể tạo mã OTP");
        }
    }

    @Override
    public OTP getOTPByEmail(String email) throws BaseException {
        try {
            return otpRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Không tìm thấy OTP"));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error get OTP by email: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể lấy mã OTP");
        }
    }

    @Override
    public OTP getOTPByCode(String code) throws BaseException {
        try {
            return otpRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Không tìm thấy OTP"));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error get OTP by code: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể lấy mã OTP");
        }
    }

    @Override
    public void verifyOTP(String email, String code) throws BaseException {
        try {
            OTP otp = otpRepository.findByEmail(email).orElseThrow(() -> new UnauthorizedException("OTP không hợp lệ"));

            if (!otp.getCode().equals(code)) {
                otp.setFailedAttempt(otp.getFailedAttempt() + 1);
                otpRepository.save(otp);

                if (otp.getFailedAttempt() >= failedAttempt) {
                    otp.setState(OTPState.EXPIRED);
                    otp.setTryAgainAt(LocalDateTime.now().plusSeconds(tryAfter));
                    otpRepository.save(otp);

                    throw new ForbiddenException("Vui lòng thử lại sau " + tryAfter + " giây");
                }

                throw new UnauthorizedException("Mã OTP không hợp lệ");
            }

            if (otp.getState() == OTPState.EXPIRED) {
                throw new GoneException("Mã OTP đã hết hạn");
            }

            if (otp.getState() == OTPState.VERIFIED) {
                throw new GoneException("Mã OTP đã được xác thực");
            }

            otp.setState(OTPState.VERIFIED);
            otpRepository.save(otp);
        } catch (UnauthorizedException | ForbiddenException | GoneException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error verify OTP: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể xác thực OTP");
        }
    }

    @Override
    public void deleteOTP() throws BaseException {
        try {
            otpRepository.delete();
        } catch (Exception e) {
            log.error("Error delete OTP: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể xóa mã OTP");
        }
    }
}
