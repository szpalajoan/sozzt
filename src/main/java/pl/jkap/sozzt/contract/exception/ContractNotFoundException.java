package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;

public class ContractNotFoundException extends ContractException {

    public ContractNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "contract.not-found.error");
    }
}
