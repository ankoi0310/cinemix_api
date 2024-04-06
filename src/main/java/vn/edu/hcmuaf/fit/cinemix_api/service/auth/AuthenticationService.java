package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenResponse;

public interface AuthenticationService extends UserDetailsService {
    RegisterResponse register(HttpServletRequest httpRequest,RegisterRequest registerRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest) throws Exception;

    void verifyToken(String token);

    void forgotPassword(HttpServletRequest httpServletRequest,String email) throws Exception;

    void resetPassword(String token, String newPassword);

    JWTRefreshTokenResponse refreshToken(String token);
}
