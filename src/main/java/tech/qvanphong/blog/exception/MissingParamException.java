package tech.qvanphong.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MissingParamException extends RuntimeException{
    public MissingParamException(String message) {
        super("Missing parameter: " + message);
    }
}
