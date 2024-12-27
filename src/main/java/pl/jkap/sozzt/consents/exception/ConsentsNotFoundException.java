package pl.jkap.sozzt.consents.exception;

import org.springframework.http.HttpStatus;

public class ConsentsNotFoundException extends ConsentsException {

    public ConsentsNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "consents.not-found.error");
    }
}
