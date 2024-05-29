package antigravity.common;

import antigravity.constant.ExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException e) {
        ExceptionCode exceptionCode = e.getExceptionCode();

        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(ExceptionResponse.builder()
                        .code(exceptionCode.toString())
                        .message(exceptionCode.getMessage())
                        .build()
                );
    }
}
