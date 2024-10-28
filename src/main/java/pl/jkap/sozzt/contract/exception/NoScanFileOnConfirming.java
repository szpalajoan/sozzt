package pl.jkap.sozzt.contract.exception;

import org.springframework.http.HttpStatus;

public class NoScanFileOnConfirming  extends ContractException{
    public NoScanFileOnConfirming(String message) {
        super(message, HttpStatus.BAD_REQUEST, "contract.no-scan.error");
    }

}
