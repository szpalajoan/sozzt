package pl.jkap.sozzt.contractsecurity.exception;

abstract class ContractSecurityException extends RuntimeException {

    public ContractSecurityException(String message) {
        super(message);
    }
}
