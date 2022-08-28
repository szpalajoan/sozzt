package pl.jkap.sozzt.consent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConsentNotFoundException extends RuntimeException {
    public ConsentNotFoundException() {
        super("There is no consent with given id");
    }
}
