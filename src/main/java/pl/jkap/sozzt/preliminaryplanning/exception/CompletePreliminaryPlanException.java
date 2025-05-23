package pl.jkap.sozzt.preliminaryplanning.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

public class CompletePreliminaryPlanException extends SozztException {

    public CompletePreliminaryPlanException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "preliminary-plan.complete.error");
    }
}
