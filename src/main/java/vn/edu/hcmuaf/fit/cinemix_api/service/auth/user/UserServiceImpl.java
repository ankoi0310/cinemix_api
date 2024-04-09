package vn.edu.hcmuaf.fit.cinemix_api.service.auth.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BadRequestException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.ResourcesExistException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppRole;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.UserInfo;
import vn.edu.hcmuaf.fit.cinemix_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AppRoleRepository appRoleRepository;

    @Override
    public AppUser createNewUser(RegisterRequest request) throws Exception {
        if(StringUtils.isBlank(request.getEmail()))
        {
            throw new BadRequestException("Email could not be null");
        }

        if(StringUtils.isBlank(request.getPhone()))
        {
            throw new BadRequestException("Phone could not be null");
        }

        Optional<AppUser> appUserByPhone = userRepository.findByPhone(request.getPhone());
        Optional<AppUser> appUserByEmail = userRepository.findByEmail(request.getEmail());
        if (appUserByEmail.isPresent()) {
            throw new ResourcesExistException("Email " +request.getEmail());
        }

        if (appUserByPhone.isPresent()) {
            throw new ResourcesExistException("Phone "+request.getPhone());
        }
        AppRole defaultAppRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE)
                .orElseThrow( () ->  new NotFoundException("Default role not found"));

        UserInfo userInfo = UserInfo.builder()
                .fullName(request.getFullName())
                .birthday(request.getBirthday())
                .build();
        AppUser newUser = AppUser.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .userInfo(userInfo)
                .appRole(defaultAppRole)
                .build();
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public void lockUser(AppUser user) {

    }

    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Email not found :"+email));
    }

    @Override
    public void unlockUser(String email) {
        AppUser user = findByEmail(email);
        if(user.getLockedTime() != null)
        {
            if(user.getLockedTime().plusMinutes(AppConstant.USER_LOCK_TIME).isBefore(LocalDateTime.now()))
            {
                user.setFailedAttempts(0);
                user.setLockedTime(null);
                user.setAccountNonLocked(true);
                userRepository.save(user);
            }
        }
    }

    @Override
    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public AppUser login(LoginRequest loginRequest) {
        AppUser appUser = findByEmail(loginRequest.getEmail());
        if(!appUser.getEmailVerified()&& !appUser.getPhoneVerified())
        {
            throw new ResourceAccessException("User need to be verified ");
        }
        if(!appUser.isAccountNonLocked())
        {
            LocalDateTime now =LocalDateTime.now();
            if(appUser.getLockedTime().plusMinutes(AppConstant.USER_LOCK_TIME).isBefore(now))
            {

                appUser.setFailedAttempts(0);
                appUser.setLockedTime(null);
                appUser.setAccountNonLocked(true);
                save(appUser);
            }
        }
        return appUser;
    }



}
