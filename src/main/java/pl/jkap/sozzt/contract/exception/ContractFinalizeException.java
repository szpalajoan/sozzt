package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContractFinalizeException extends ContractException{
    public ContractFinalizeException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "contract.finalize.error");
    }
}
