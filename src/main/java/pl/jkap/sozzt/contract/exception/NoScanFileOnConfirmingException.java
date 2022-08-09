package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NoScanFileOnConfirmingException extends ContractException{
    public NoScanFileOnConfirmingException(String message) {
        super(message);
    }

}
