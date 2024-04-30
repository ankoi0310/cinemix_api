package vn.edu.hcmuaf.fit.cinemix_api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.*;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.AuthenticationService;
import vn.edu.hcmuaf.fit.cinemix_api.service.otp.OTPService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final OTPService otpService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody RegisterRequest request) throws BaseException {
        authenticationService.register(request);
        return ResponseEntity.ok(HttpResponse.success("Đăng ký thành công!"));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok().body(HttpResponse.success(loginResponse, "Đăng nhập thành công!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<HttpResponse> forgotPassword(
            HttpServletRequest httpServletRequest,
            @RequestBody ForgotPasswordRequest request
    ) throws BaseException {
        authenticationService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(HttpResponse.success("Vui lòng kiểm tra email để lấy mã OTP!"));
    }

    @PostMapping("/verify")
    public ResponseEntity<HttpResponse> verifyOTP(@RequestParam String email, @RequestParam String code) throws BaseException {
        otpService.verifyOTP(email, code);
        return ResponseEntity.ok(HttpResponse.success("Xác thực OTP thành công!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordRequest request) throws BaseException {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(HttpResponse.success("Reset password successfully"));
    }
}
