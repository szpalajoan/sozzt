package pl.jkap.sozzt.documentation.exception;

import org.springframework.http.HttpStatus;

public class DocumentationNotFoundException extends DocumentationException {

    public DocumentationNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "documentation.not-found.error");
    }
}
