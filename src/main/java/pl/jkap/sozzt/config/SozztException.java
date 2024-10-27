package pl.jkap.sozzt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public abstract class SozztException extends RuntimeException {

    HttpStatus status;
    String codeError;

    public SozztException(String message, HttpStatus status, String codeError) {
        super(message);
        this.codeError = codeError;
        this.status = status;
    }

}
