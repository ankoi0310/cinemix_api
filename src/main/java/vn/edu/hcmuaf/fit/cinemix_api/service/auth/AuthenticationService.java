package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.*;

public interface AuthenticationService extends UserDetailsService {
    RegisterResponse register(RegisterRequest registerRequest) throws NotFoundException;

    LoginResponse login(LoginRequest loginRequest);

    void verifyToken(String token);

    void forgotPassword(String email);

    void resetPassword(String token, String newPassword);

    String refreshToken(String token);
}
