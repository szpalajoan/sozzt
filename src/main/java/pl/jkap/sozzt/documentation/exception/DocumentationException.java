package pl.jkap.sozzt.documentation.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

abstract class DocumentationException extends SozztException {
    public DocumentationException(String message, HttpStatus status, String codeError) {
        super(message, status, codeError);
    }
}
