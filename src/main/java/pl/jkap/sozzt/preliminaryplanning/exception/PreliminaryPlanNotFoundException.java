package pl.jkap.sozzt.preliminaryplanning.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

public class PreliminaryPlanNotFoundException extends SozztException {
    public PreliminaryPlanNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "preliminary-plan.not-found.error");
    }
}
