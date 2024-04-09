package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword.PasswordResetRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.verificationUser.VerificationUserRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.AppUser;
import vn.edu.hcmuaf.fit.cinemix_api.entity.JWTRefreshToken;
import vn.edu.hcmuaf.fit.cinemix_api.entity.PasswordResetOTP;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationUser;
import vn.edu.hcmuaf.fit.cinemix_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.jwtRefreshToken.JWTRefreshTokenService;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.passwordReset.PasswordResetOTPService;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.user.UserService;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.verifyUser.VerificationUserService;
import vn.edu.hcmuaf.fit.cinemix_api.service.mail.MailService;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final AppRoleRepository appRoleRepository;
    private final VerificationUserService verificationUserService;

    private final JWTRefreshTokenService jwtRefreshTokenService;

    private final JwtProvider jwtProvider;

    private final MailService mailService;
    private final PasswordResetOTPService passwordResetOTPService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        AppUser newUser = userService.createNewUser(registerRequest);
        // create verification token
        VerificationUser verificationUser = verificationUserService.create(newUser);
        // send email verification
        mailService.sendVerifyEmail(verificationUser);
        return RegisterResponse.builder()
                               .username(newUser.getUsername())
                               .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
       AppUser appUser = userService.login(loginRequest);
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
        userService.save(appUser);
    }

    @Override
    public void forgotPassword(String email)  throws  Exception{
        AppUser appUser =userService.findByEmail(email);
        PasswordResetOTP passwordResetOTP = passwordResetOTPService.create(appUser);
        //send email
        mailService.sendResetPasswordEmail(passwordResetOTP);
    }

    @Override
    public void resetPassword(PasswordResetRequest request) {
        PasswordResetOTP passwordResetOTP = passwordResetOTPService.findByRequest(request);
        AppUser appUser = passwordResetOTP.getUser();
        appUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(appUser);
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
        AppUser appUser = userService.findByEmail(username);

        return new User(appUser.getEmail(),
                appUser.getPassword(),
                appUser.getAuthorities());
    }

    @Override
    public void resendVerifyUserEmail(String email) throws  Exception
    {
        AppUser user = userService.findByEmail(email);
        VerificationUser verificationUser = verificationUserService.create(user);
        mailService.sendVerifyEmail(verificationUser);
    }
}
