package pl.jkap.sozzt.contract.old.service.stepContract;

import pl.jkap.sozzt.contract.old.model.ContractStep;

public interface Step {
    public ContractStep getContractStep();
    public Step validateStep();
}
