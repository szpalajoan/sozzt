package pl.jkap.sozzt.contractsecurity.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

abstract class ContractSecurityException extends SozztException {

    public ContractSecurityException(String message) {
        super(message, HttpStatus.FORBIDDEN, "contract.security.exception");
    }
}
