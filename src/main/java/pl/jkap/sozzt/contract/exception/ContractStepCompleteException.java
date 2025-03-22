package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;

public class ContractStepCompleteException extends ContractException{
    public ContractStepCompleteException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "contract.step.finalize.error");
    }
}
