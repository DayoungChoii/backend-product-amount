package antigravity.constant;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}
