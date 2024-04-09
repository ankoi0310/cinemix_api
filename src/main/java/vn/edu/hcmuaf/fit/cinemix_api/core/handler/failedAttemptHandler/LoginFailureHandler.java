package vn.edu.hcmuaf.fit.cinemix_api.core.handler.failedAttemptHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;

import java.time.LocalDateTime;

@Slf4j
public class LoginFailureHandler implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        if(event.getException().getClass().equals(UsernameNotFoundException.class))
        {
            return;
        }

        String email = event.getAuthentication().getName();

        AppUser user =userRepository.findByEmail(email).orElse(null);
        if(user != null)
        {
            user.setFailedAttempts(user.getFailedAttempts()+1);
            if((user.getFailedAttempts())>= AppConstant.MAX_FAILED_ATTEMPT)
            {
                user.setAccountNonLocked(false);
                user.setLockedTime(LocalDateTime.now());
            }
            userRepository.save(user);
        }

    }

}
