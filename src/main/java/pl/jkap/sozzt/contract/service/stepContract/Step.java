package pl.jkap.sozzt.contract.service.stepContract;

import pl.jkap.sozzt.contract.model.ContractStep;

public interface Step {

    public ContractStep getFileType();
    public Step validateStep();
}
