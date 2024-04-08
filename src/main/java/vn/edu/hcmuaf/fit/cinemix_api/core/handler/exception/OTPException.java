package vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class OTPException extends RuntimeException {

    public OTPException(String otp, String message) {
        super(String.format("Failed for [%s]: %s", otp, message));
    }
}