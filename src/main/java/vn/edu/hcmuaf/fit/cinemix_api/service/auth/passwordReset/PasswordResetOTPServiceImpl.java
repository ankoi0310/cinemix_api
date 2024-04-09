package vn.edu.hcmuaf.fit.cinemix_api.service.auth.passwordReset;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.MaxFailedAttemptsException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.OTPException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword.PasswordResetRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetOTP;
import vn.edu.hcmuaf.fit.cinemix_api.repository.passwordResetOTP.PasswordResetOTPRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetOTPServiceImpl implements PasswordResetOTPService {
    private final PasswordResetOTPRepository passwordResetOTPRepository;
    @Override
    public PasswordResetOTP create(AppUser user) {
        String OTP = RandomStringUtils.randomNumeric(AppConstant.OTP_LENGTH);
        LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(AppConstant.PASSWORD_RESET_OTP_EXPIRED_DATE);
        PasswordResetOTP passwordResetOTP = PasswordResetOTP.builder()
                .OTP(OTP)
                .user(user)
                .expiredDate(expiredDate)
                .build();
        PasswordResetOTP availablePasswordResetOTP = passwordResetOTPRepository.findByUser(user.getId())
                .orElse(passwordResetOTP);
        if(availablePasswordResetOTP.getId() != null )
        {
            availablePasswordResetOTP.setOTP(OTP);
            availablePasswordResetOTP.setExpiredDate(expiredDate);
            availablePasswordResetOTP.setFailedAttempts(0);
        }
        passwordResetOTPRepository.save(availablePasswordResetOTP);
        return availablePasswordResetOTP;
    }

    @Override
    public PasswordResetOTP findByRequest(PasswordResetRequest request) {
        PasswordResetOTP passwordResetOTP = passwordResetOTPRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email does not exist"));
        if(passwordResetOTP.isExpired())
        {
            throw new OTPException(request.getOTP(),"OTP is expired");
        }
        if(!StringUtils.equals(passwordResetOTP.getOTP(),request.getOTP()))
        {
            if((passwordResetOTP.getFailedAttempts()+1)>= AppConstant.MAX_FAILED_ATTEMPT)
            {
                // Because transaction will roll back, when this exception thrown, failedAttempts still is 2, we need plus 1
                throw new MaxFailedAttemptsException("Max failed attempts. Please resend email to get new OTP");
            }
            throw new OTPException(request.getEmail(),"OTP did not match to reset password");
        }

        return passwordResetOTP;
    }

    @Override
    public void delete(PasswordResetOTP passwordResetOTP) {
        passwordResetOTPRepository.delete(passwordResetOTP);
    }

    @Override
    public void increaseFailedAttempts(String email) {
        PasswordResetOTP passwordResetOTP = passwordResetOTPRepository.findByEmail(email).orElse(null);
        if(passwordResetOTP!= null)
        {
            passwordResetOTP.setFailedAttempts(passwordResetOTP.getFailedAttempts()+1);
            passwordResetOTPRepository.save(passwordResetOTP);
        }
    }


}
