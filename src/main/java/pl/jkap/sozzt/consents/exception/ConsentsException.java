package pl.jkap.sozzt.consents.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

abstract class ConsentsException extends SozztException {

    public ConsentsException(String message, HttpStatus status, String codeError) {
        super(message, status, codeError);
    }
}
