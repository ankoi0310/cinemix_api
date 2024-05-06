package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.mail.MailService;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.*;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;
import vn.edu.hcmuaf.fit.cinemix_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;
import vn.edu.hcmuaf.fit.cinemix_api.service.otp.OTPService;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;

    private final JwtProvider jwtProvider;
    private final OTPService otpService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest registerRequest) throws BaseException {
        try {
            Optional<AppUser> appUserEmail = userRepository.findByEmail(registerRequest.getEmail());
            if (appUserEmail.isPresent()) {
                throw new NotFoundException("Email đã tồn tại");
            }

            Optional<AppUser> appUserPhone = userRepository.findByPhone(registerRequest.getPhone());

            if (appUserPhone.isPresent()) {
                throw new NotFoundException("Số điện thoại đã tồn tại");
            }

            AppRole defaultAppRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE).orElse(null);
            if (defaultAppRole == null) {
                throw new ServiceUnavailableException("Không tìm thấy quyền mặc định");
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

            // Create OTP
            OTP otp = otpService.generateOTP(newUser.getEmail(), OTPType.VERIFY_EMAIL);

            // Send OTP
            mailService.sendOTP(newUser.getEmail(), otp.getCode(), otp.getType());
        } catch (NotFoundException | ServiceUnavailableException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể tạo tài khoản mới");
        }
    }

    @Override
    public void verifyRegister(String code) throws BaseException {
        OTP otp = otpService.getOTPByCode(code);
        if (otp.getType() != OTPType.VERIFY_EMAIL) {
            throw new BadRequestException("Mã OTP không hợp lệ");
        }

        AppUser appUser = userRepository.findByEmail(otp.getEmail())
                                        .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

        appUser.setEmailVerified(true);
        appUser.setEnabled(true);
        userRepository.save(appUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws BaseException {
        try {
            AppUser appUser = userRepository.findByUsername(loginRequest.getUsername())
                                            .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
                throw new BadRequestException("Mật khẩu không đúng");
            }

            if (!appUser.getEnabled()) {
                throw new BadRequestException("Tài khoản chưa được kích hoạt");
            }

            // Create authentication
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    appUser,
                    null,
                    appUser.getAuthorities()
            );

            // Generate token
            String token = jwtProvider.generateAccessToken(authentication);

            // Generate refresh token
            String refreshToken = jwtProvider.generateRefreshToken(authentication);

            return LoginResponse.builder()
                                .fullName(appUser.getUserInfo().getFullName())
                                .email(appUser.getEmail())
                                .accessToken(token)
                                .refreshToken(refreshToken)
                                .build();
        } catch (NotFoundException | BadRequestException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể đăng nhập");
        }
    }

    @Override
    public void forgotPassword(String email) throws BaseException {
//        mailService.sendResetPasswordEmail(passwordResetOTP);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepository.findByEmail(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản");
        }

        return appUser.get();
    }
}
