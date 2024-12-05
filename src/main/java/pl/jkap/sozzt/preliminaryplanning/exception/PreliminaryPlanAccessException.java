package pl.jkap.sozzt.preliminaryplanning.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

public class PreliminaryPlanAccessException extends SozztException {
    public PreliminaryPlanAccessException(String message) {
        super(message, HttpStatus.FORBIDDEN, "preliminary-plan.access.error");
    }
}
