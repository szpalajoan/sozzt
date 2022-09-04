package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WithdrawalException extends ContractException {
    public WithdrawalException(String message) {
        super(message);
    }
}
