package vn.edu.hcmuaf.fit.cinemix_api.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cinemix_api.controller.exceptionHandle.ControllerExceptionHandler;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.ForgotPassword.ForgotPasswordRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.LoginResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.RegisterResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenRequest;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.jwtRefreshToken.JWTRefreshTokenResponse;
import vn.edu.hcmuaf.fit.cinemix_api.dto.auth.resetPassword.ResetPasswordRequest;
import vn.edu.hcmuaf.fit.cinemix_api.entity.VerificationToken;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.AuthenticationService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends ControllerExceptionHandler {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest loginRequest) throws Exception
    {
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body(HttpResponse.success(loginResponse,"Login successfully"));
    }
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(HttpServletRequest httpServletRequest, @RequestBody RegisterRequest registerRequest) throws  Exception
    {
        RegisterResponse response = authenticationService.register(httpServletRequest,registerRequest);
        return ResponseEntity.ok(HttpResponse.success(response,"Register successfully"));
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<HttpResponse> forgotPassword(HttpServletRequest httpServletRequest,@RequestBody ForgotPasswordRequest request) throws Exception
    {
        authenticationService.forgotPassword(httpServletRequest,request.getEmail());
        return ResponseEntity.ok(HttpResponse.success("Token sent via email"));

    }
    @PostMapping("/resetPassword")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordRequest request)
    {
        authenticationService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(HttpResponse.success("Reset password successfully"));

    }
    @GetMapping("/verifyToken")
    public ResponseEntity<HttpResponse> verifyToken(@Param("token") String token)
    {
        authenticationService.verifyToken(token);
        return ResponseEntity.ok(HttpResponse.success("Verify token successfully, User can login now"));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<HttpResponse> refreshToken(@RequestBody JWTRefreshTokenRequest request)
    {
        JWTRefreshTokenResponse response = authenticationService.refreshToken(request.getToken());
        return ResponseEntity.ok(HttpResponse.success(response,"Refresh token successfully"));
    }

}
