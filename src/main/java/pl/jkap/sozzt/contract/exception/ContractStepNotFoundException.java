package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;

public class ContractStepNotFoundException extends ContractException{
    public ContractStepNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "contract.step.not-found.error");
    }
}
