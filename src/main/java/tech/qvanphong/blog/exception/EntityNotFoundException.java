package tech.qvanphong.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Data not found")
public class EntityNotFoundException extends RuntimeException{
}
