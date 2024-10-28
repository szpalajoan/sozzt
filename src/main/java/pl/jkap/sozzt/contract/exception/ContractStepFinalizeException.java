package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;

public class ContractStepFinalizeException extends ContractException{
    public ContractStepFinalizeException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "contract.step.finalize.error");
    }
}
