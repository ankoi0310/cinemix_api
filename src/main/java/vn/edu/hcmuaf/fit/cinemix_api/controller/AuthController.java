package vn.edu.hcmuaf.fit.cinemix_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.*;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.AuthenticationService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody RegisterRequest request) throws BaseException {
        RegisterResponse registerResponse = authenticationService.register(request);
        return ResponseEntity.ok(HttpResponse.success(registerResponse, "Vui lòng kiểm tra email để lấy mã OTP!"));
    }

    @PostMapping("/verify")
    public ResponseEntity<HttpResponse> verify(@RequestParam String code) throws BaseException {
        String message = authenticationService.verify(code);
        return ResponseEntity.ok(HttpResponse.success(message));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok().body(HttpResponse.success(loginResponse, "Đăng nhập thành công!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<HttpResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) throws BaseException {
        authenticationService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(HttpResponse.success("Vui lòng kiểm tra email để lấy mã OTP!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordRequest request) throws BaseException {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(HttpResponse.success("Đặt lại mật khẩu thành công!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<HttpResponse> refreshToken() throws BaseException {
        RefreshTokenResponse refreshTokenResponse = authenticationService.refreshToken();
        return ResponseEntity.ok(HttpResponse.success(refreshTokenResponse, "Làm mới token thành công!"));
    }
}
