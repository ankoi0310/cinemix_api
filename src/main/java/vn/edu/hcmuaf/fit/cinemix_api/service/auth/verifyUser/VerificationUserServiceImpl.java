package vn.edu.hcmuaf.fit.cinemix_api.service.auth.verifyUser;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.MaxFailedAttemptsException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.OTPException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.verificationUser.VerificationUserRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationUser;
import vn.edu.hcmuaf.fit.cinemix_api.repository.verificationUser.VerificationUserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationUserServiceImpl implements VerificationUserService {
    private final VerificationUserRepository verificationUserRepository;

    @Override
    public VerificationUser create(AppUser user) {
        String OTP = RandomStringUtils.randomNumeric(AppConstant.OTP_LENGTH);
        LocalDateTime expiredDate = LocalDateTime.now().plusDays(AppConstant.VERIFICATION_OTP_EXPIRED_DATE);
        VerificationUser verificationUser = VerificationUser.builder()
                .OTP(OTP)
                .user(user)
                .expiredDate(expiredDate)
                .build();
        VerificationUser availableVerificationUser =verificationUserRepository.findByUser(user.getId()).orElse(verificationUser);
        if(availableVerificationUser.getId() != null)
        {
            availableVerificationUser.setOTP(OTP);
            availableVerificationUser.setExpiredDate(expiredDate);
            availableVerificationUser.setFailedAttempts(0);
        }
        verificationUserRepository.save(availableVerificationUser);
        return availableVerificationUser;
    }

    @Override
    @Transactional(noRollbackFor = { OTPException.class })
    public VerificationUser findByRequest(VerificationUserRequest request) {
        if(StringUtils.isBlank(request.getEmail()))
        {
            throw new IllegalArgumentException("Email could not be null");
        }

        VerificationUser verificationUser = verificationUserRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("Email does not exist"));
        if(!StringUtils.equals(verificationUser.getOTP(),request.getOTP()))
        {
            if((verificationUser.getFailedAttempts()+1)>=AppConstant.MAX_FAILED_ATTEMPT)
            {
                throw new MaxFailedAttemptsException("Max failed attempts. Please re-send an email to get new OTP");
            }
            throw new OTPException(request.getEmail(),"OTP did not match to verify user");
        }

        if(verificationUser.isExpired())
        {
            throw new OTPException(request.getEmail(),"OTP had been expired. Please re-send an email to get new OTP");
        }
        return verificationUser;
    }

    @Override
    public void increaseFailedAttempts(String email) {
        VerificationUser verificationUser = verificationUserRepository.findByEmail(email).orElse(null);
        if(verificationUser != null)
        {
            verificationUser.setFailedAttempts(verificationUser.getFailedAttempts()+1);
            verificationUserRepository.save(verificationUser);
        }
    }


}
