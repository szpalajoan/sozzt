package pl.jkap.sozzt.remark.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

public class RemarkNotFoundException extends SozztException {
    public RemarkNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "remark.not-found.error");
    }
}
