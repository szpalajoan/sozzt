package pl.jkap.sozzt.contract.exception;

public class ContractNotFoundException extends ContractException {
    public ContractNotFoundException() {
        super("There is no contract with given id");
    }
}
