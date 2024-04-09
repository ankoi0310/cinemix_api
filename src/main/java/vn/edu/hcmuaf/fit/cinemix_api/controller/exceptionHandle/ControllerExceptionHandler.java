package vn.edu.hcmuaf.fit.cinemix_api.controller.exceptionHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.MaxFailedAttemptsException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.ResourcesExistException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.OTPException;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.passwordReset.PasswordResetOTPService;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.user.UserService;
import vn.edu.hcmuaf.fit.cinemix_api.service.auth.verifyUser.VerificationUserService;

@RestControllerAdvice
public class ControllerExceptionHandler {


    private VerificationUserService verificationUserService;


    private UserService userService;

    private PasswordResetOTPService passwordResetOTPService;

    @ExceptionHandler(value = OTPException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpResponse handleOTPException(OTPException ex, WebRequest request) {
        String email = ex.getMessage().substring(ex.getMessage().indexOf('[')+1,ex.getMessage().indexOf(']'));
        if(ex.getMessage().indexOf("OTP did not match to reset password") != -1)
        {
            passwordResetOTPService.increaseFailedAttempts(email);
        }
        if(ex.getMessage().indexOf("OTP did not match to verify user") != -1)
        {
            verificationUserService.increaseFailedAttempts(email);
        }
        return HttpResponse.fail(HttpStatus.UNPROCESSABLE_ENTITY.value(),ex.getMessage());

    }
    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpResponse handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    @ExceptionHandler(value = ResourcesExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public HttpResponse handleResourcesExistException(ResourcesExistException ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.CONFLICT.value(),ex.getMessage());
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpResponse handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }
    @ExceptionHandler(value = MaxFailedAttemptsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpResponse handleMaxFailedAttemptsException(MaxFailedAttemptsException ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.FORBIDDEN.value(),ex.getMessage());
    }
    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpResponse handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Password is not correct, you have total 3 times attempt");
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse handleException(Exception ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    }


}
