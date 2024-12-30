package pl.jkap.sozzt.consents.exception;

import org.springframework.http.HttpStatus;

public class ConsentUpdateException extends ConsentsException {

    public ConsentUpdateException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "consents.update.error");
    }
}
