package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

abstract class ContractException extends SozztException {

    public ContractException(String message, HttpStatus status, String codeError) {
        super(message, status, codeError);
    }
}
