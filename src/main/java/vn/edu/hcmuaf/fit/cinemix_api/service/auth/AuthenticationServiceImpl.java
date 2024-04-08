package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.thymeleaf.util.StringUtils;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.ResourcesExistException;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword.PasswordResetRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.verificationUser.VerificationUserRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;
import vn.edu.hcmuaf.fit.cinemix_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.jwtRefreshToken.JWTRefreshTokenService;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.passwordReset.PasswordResetOTPService;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.verifyUser.VerificationUserService;
import vn.edu.hcmuaf.fit.cinemix_api.service.mail.MailService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;
    private final VerificationUserService verificationUserService;

    private final JWTRefreshTokenService jwtRefreshTokenService;

    private final JwtProvider jwtProvider;

    private final MailService mailService;
    private final PasswordResetOTPService passwordResetOTPService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        // check if the username is already taken
        Optional<AppUser> appUserByPhone = userRepository.findByPhone(registerRequest.getPhone());
        Optional<AppUser> appUserByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (appUserByEmail.isPresent()) {
            throw new ResourcesExistException("Email " +registerRequest.getEmail());
        }

        if (appUserByPhone.isPresent()) {
            throw new ResourcesExistException("Phone "+registerRequest.getPhone());
        }

        // create new user
        AppRole defaultAppRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE).orElse(null);
        if (defaultAppRole == null) {
            throw new NotFoundException("Default role not found");
        }

        UserInfo userInfo = UserInfo.builder()
                                    .fullName(registerRequest.getFullName())
                                    .birthday(registerRequest.getBirthday())
                                    .build();
        AppUser newUser = AppUser.builder()
                                 .email(registerRequest.getEmail())
                                 .phone(registerRequest.getPhone())
                                 .password(passwordEncoder.encode(registerRequest.getPassword()))
                                 .userInfo(userInfo)
                                 .appRole(defaultAppRole)
                                 .build();
        userRepository.save(newUser);

        // create verification token
        VerificationUser verificationUser = verificationUserService.create(newUser);
        // send email verification
//        mailService.sendVerifyEmail(verificationUser);

        return RegisterResponse.builder()
                               .username(newUser.getUsername())
                               .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        AppUser appUser = userRepository.findByEmail(loginRequest.getEmail()).
                orElseThrow(()-> new UsernameNotFoundException("Email not found : "+loginRequest.getEmail()));
        if(!appUser.getEmailVerified()&& !appUser.getPhoneVerified())
        {
            throw new ResourceAccessException("User need to be verified ");
        }
        if(!appUser.isAccountNonLocked())
        {
            LocalDateTime now =LocalDateTime.now();
            if(appUser.getLockedTime().plusMinutes(AppConstant.USER_LOCK_TIME).isBefore(now))
            {
                appUser.setLockedTime(null);
                appUser.setAccountNonLocked(true);
                userRepository.save(appUser);
            }
        }
        String jwtToken = jwtProvider.generateToken(appUser);
        JWTRefreshToken refreshToken = jwtRefreshTokenService.createOrUpdate(appUser);

        return LoginResponse.builder()
                .email(appUser.getEmail())
                .accessToken(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public void verifyUser(VerificationUserRequest request) {
        VerificationUser verificationUser = verificationUserService.findByRequest(request);
        AppUser appUser = verificationUser.getUser();
        appUser.setEmailVerified(true);
        appUser.setEnabled(true);
        userRepository.save(appUser);
    }

    @Override
    public void forgotPassword(String email)  throws  Exception{
        AppUser appUser =userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("Email does not exist: "+email)
        );
        PasswordResetOTP passwordResetOTP = passwordResetOTPService.create(appUser);
        //send email
//        mailService.sendResetPasswordEmail(passwordResetOTP);
    }

    @Override
    public void resetPassword(PasswordResetRequest request) {
        PasswordResetOTP passwordResetOTP = passwordResetOTPService.findByRequest(request);
        AppUser appUser = passwordResetOTP.getUser();
        appUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(appUser);
        passwordResetOTPService.delete(passwordResetOTP);
    }

    @Override
    public JWTRefreshTokenResponse refreshToken(String refreshToken) {
        JWTRefreshToken jwtRefreshToken = jwtRefreshTokenService.findByToken(refreshToken);
        String token= jwtProvider.generateToken(jwtRefreshToken.getUser());
        return JWTRefreshTokenResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(username).
                orElseThrow(()-> new UsernameNotFoundException("Email not found : $"+username));

        return new User(appUser.getEmail(),
                appUser.getPassword(),
                appUser.getAuthorities());
    }

    @Override
    public void resendVerifyUserEmail(String email)
    {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Not found user by email :" + email));
        VerificationUser verificationUser = verificationUserService.create(user);
//        mailService.sendVerifyEmail(verificationUser);
    }
}
