package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotAllConsentsAreConfirmedException extends ContractException{
    public NotAllConsentsAreConfirmedException(String message) {
        super(message);
    }

}
