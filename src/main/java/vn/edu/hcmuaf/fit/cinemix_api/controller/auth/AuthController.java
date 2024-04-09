package vn.edu.hcmuaf.fit.cinemix_api.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.fit.cinemix_api.controller.exceptionHandle.ControllerExceptionHandler;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.ForgotPassword.ForgotPasswordRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword.PasswordResetRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.verificationUser.VerificationUserRequest;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.AuthenticationService;
import vn.edu.hcmuaf.fit.cinemix_api.service.mail.MailService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends ControllerExceptionHandler {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    private final MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest loginRequest) throws Exception
    {
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body(HttpResponse.success(loginResponse,"Login successfully"));
    }
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register( @RequestBody RegisterRequest registerRequest) throws  Exception
    {
        RegisterResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.ok(HttpResponse.success(response,"Register successfully"));
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<HttpResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) throws Exception
    {
        authenticationService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(HttpResponse.success("OTP was sent to your email"));

    }
    @PostMapping("/resetPassword")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody PasswordResetRequest request)
    {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(HttpResponse.success("Reset password successfully"));

    }
    @PostMapping("/verifyUser")
    public ResponseEntity<HttpResponse> verifyUser(@RequestBody VerificationUserRequest request)
    {
        authenticationService.verifyUser(request);
        return ResponseEntity.ok(HttpResponse.success("Verify token successfully, User can login now"));
    }

    @PostMapping("/verifyUser/resendEmail")
    public ResponseEntity<HttpResponse> resendEmail(@RequestBody VerificationUserRequest request) throws Exception
    {
        authenticationService.resendVerifyUserEmail(request.getEmail());
        return ResponseEntity.ok(HttpResponse.success("OTP was sent to your email"));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<HttpResponse> refreshToken(@RequestBody JWTRefreshTokenRequest request)
    {
        JWTRefreshTokenResponse response = authenticationService.refreshToken(request.getToken());
        return ResponseEntity.ok(HttpResponse.success(response,"Refresh token successfully"));
    }

}
