package vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.FORBIDDEN)
public class MaxFailedAttemptsException  extends  RuntimeException{

    public MaxFailedAttemptsException(String message) {
        super(String.format("Max failed attempts: %s", message));
    }

}
