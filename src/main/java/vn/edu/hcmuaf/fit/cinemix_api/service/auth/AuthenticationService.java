package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword.PasswordResetRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.verificationUser.VerificationUserRequest;

public interface AuthenticationService extends UserDetailsService {
    RegisterResponse register(RegisterRequest registerRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest) throws Exception;

    void verifyUser(VerificationUserRequest request);

    void forgotPassword(String email) throws Exception;

    void resetPassword(PasswordResetRequest request);

    JWTRefreshTokenResponse refreshToken(String token);
    public void resendVerifyUserEmail(String email);

}
