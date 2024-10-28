package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;

public class NoDataInputStep extends ContractException{
    public NoDataInputStep(String message) {
        super(message, HttpStatus.BAD_REQUEST, "contract.no-data.error");

    }
}
