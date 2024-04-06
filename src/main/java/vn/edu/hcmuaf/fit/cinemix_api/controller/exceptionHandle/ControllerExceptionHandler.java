package vn.edu.hcmuaf.fit.cinemix_api.controller.exceptionHandle;

import org.eclipse.angus.mail.smtp.SMTPAddressFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.ResourcesExistException;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.TokenException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = TokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HttpResponse handleTokenRefreshException(TokenException ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.FORBIDDEN.value(),ex.getMessage());
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
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse handleException(Exception ex, WebRequest request) {
        return HttpResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    }

}
