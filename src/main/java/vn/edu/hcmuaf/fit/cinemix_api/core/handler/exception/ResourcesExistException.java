package vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourcesExistException extends  RuntimeException{
    private static final long serialVersionUID = 2L;

    public ResourcesExistException(String resourceName) {
        super(String.format("%s is already taken",resourceName));
    }
}
