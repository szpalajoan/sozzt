package pl.jkap.sozzt.contract.exception;

abstract class ContractException extends RuntimeException {

    public ContractException(String message) {
        super(message);
    }

}
