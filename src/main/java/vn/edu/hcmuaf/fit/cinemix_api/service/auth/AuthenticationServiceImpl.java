package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import vn.edu.hcmuaf.fit.cinemix_api.core.config.mail.AppMailSender;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.ResourcesExistException;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenResponse;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;
import vn.edu.hcmuaf.fit.cinemix_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.passwordResetToken.PasswordResetTokenRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.verificationtoken.VerificationTokenRepository;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.jwtRefreshToken.JWTRefreshTokenService;
import vn.edu.hcmuaf.fit.cinemix_api.service.mail.MailService;

import javax.security.auth.login.LoginException;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final  Log log = LogFactory.getLog(getClass());
    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    private final JWTRefreshTokenService jwtRefreshTokenService;

    private final JwtProvider jwtProvider;

    private final MailService mailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(HttpServletRequest httpRequest, RegisterRequest registerRequest) throws Exception {
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
        String token = RandomStringUtils.randomAlphabetic(100);
        LocalDateTime expiredDate = LocalDateTime.now().plusDays(AppConstant.VERIFICATION_TOKEN_EXPIRED_DATE);
        VerificationToken verificationToken = VerificationToken.builder()
                                                               .token(token)
                                                               .user(newUser)
                                                               .expiredDate(expiredDate)
                                                               .build();
        verificationTokenRepository.save(verificationToken);
        // send email verification
        mailService.sendVerifyEmail(httpRequest,verificationToken);

        return RegisterResponse.builder()
                               .username(newUser.getUsername())
                               .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception{
        AppUser appUser = userRepository.findByEmail(loginRequest.getEmail()).
                orElseThrow(()-> new UsernameNotFoundException("Email not found : "+loginRequest.getEmail()));
        if(!appUser.getEmailVerified()&& !appUser.getPhoneVerified())
        {
            throw new ResourceAccessException("User need to be verified ");
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
    public void verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(
                () -> new IllegalArgumentException("Token not found")
        );
        if(verificationToken.isExpired())
        {
            throw new TokenExpiredException("Token expired", verificationToken.getExpiredDate().toInstant(ZoneOffset.UTC));
        }
        AppUser appUser = verificationToken.getUser();
        appUser.setEmailVerified(true);
        appUser.setEnabled(true);
        userRepository.save(appUser);
    }

    @Override
    public void forgotPassword(HttpServletRequest httpServletRequest,String email)  throws  Exception{
        AppUser appUser =userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("Email does not exist: "+email)
        );

        String token = RandomStringUtils.randomAlphabetic(100);
        LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(AppConstant.PASSWORD_RESET_TOKEN_EXPIRED_DATE);
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(appUser)
                .expiredDate(expiredDate)
                .build();
        PasswordResetToken availablePasswordResetToken = passwordResetTokenRepository.findByUser(appUser.getId())
                .orElse(passwordResetToken);
        if(availablePasswordResetToken.getId() != null )
        {
            availablePasswordResetToken.setToken(token);
            availablePasswordResetToken.setExpiredDate(expiredDate);
        }
        passwordResetTokenRepository.save(availablePasswordResetToken);
        //send email
        mailService.sendResetPasswordEmail(httpServletRequest,availablePasswordResetToken);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));
        if(passwordResetToken.isTokenExpired())
        {
            throw new TokenExpiredException("Token expired", passwordResetToken
                    .getExpiredDate().toInstant(ZoneOffset.UTC));
        }

        AppUser appUser = passwordResetToken.getUser();
        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(appUser);
        passwordResetTokenRepository.delete(passwordResetToken);
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
}
