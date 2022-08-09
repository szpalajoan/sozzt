package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ContractNotFoundException extends ContractException {
    public ContractNotFoundException() {
        super("There is no contract with given id");
    }
}
