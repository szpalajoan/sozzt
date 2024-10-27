package pl.jkap.sozzt.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(SozztException.class)
    public ResponseEntity<ApiError> handleExceptions(SozztException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), ex.getCodeError());
        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }

    public record ApiError(String messages, String codeError) {
    }
}
