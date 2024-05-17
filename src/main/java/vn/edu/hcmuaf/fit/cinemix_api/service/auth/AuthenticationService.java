package vn.edu.hcmuaf.fit.cinemix_api.service.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.*;

public interface AuthenticationService extends UserDetailsService {
    RegisterResponse register(RegisterRequest request) throws BaseException;

    String verify(String code) throws BaseException;

    LoginResponse login(LoginRequest loginRequest) throws BaseException;

    RefreshTokenResponse refreshToken() throws BaseException;

    void forgotPassword(String email) throws BaseException;

    void resetPassword(ResetPasswordRequest request) throws BaseException;

    void changePassword(ChangePasswordRequest request) throws BaseException;
}
