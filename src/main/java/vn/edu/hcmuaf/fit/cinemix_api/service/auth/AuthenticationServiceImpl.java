package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.*;
import vn.edu.hcmuaf.fit.cinemix_api.entity.*;
import vn.edu.hcmuaf.fit.cinemix_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.user.UserRepository;
import vn.edu.hcmuaf.fit.cinemix_api.repository.verificationtoken.VerificationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws NotFoundException {
        // check if the username is already taken
        Optional<AppUser> appUser = userRepository.findByEmailOrPhone(registerRequest.getEmail(),
                registerRequest.getPhone());
        if (appUser.isPresent()) {
            throw new RuntimeException("Username is already taken");
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
                                 .password(registerRequest.getPassword())
                                 .userInfo(userInfo)
                                 .appRole(defaultAppRole)
                                 .build();

        userRepository.save(newUser);

        // create verification token
        UUID token = UUID.randomUUID();
        LocalDateTime expiredDate = LocalDateTime.now().plusDays(AppConstant.VERIFICATION_TOKEN_EXPIRED_DATE);
        VerificationToken verificationToken = VerificationToken.builder()
                                                               .token(token)
                                                               .email(newUser.getEmail())
                                                               .expiredDate(expiredDate)
                                                               .build();

        // send email verification
        verificationTokenRepository.save(verificationToken);

        return RegisterResponse.builder()
                               .username(newUser.getUsername())
                               .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public void verifyToken(String token) {

    }

    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }

    @Override
    public String refreshToken(String token) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepository.findByEmail(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return appUser.get();
    }
}
