package pl.jkap.sozzt.contract.old.service.stepContract;

import pl.jkap.sozzt.contract.old.model.ContractStep;

public class WaitingToPreliminaryStep implements Step {
    @Override
    public ContractStep getContractStep() {
        return ContractStep.WAITING_TO_PRELIMINARY_MAP;
    }

    @Override
    public Step validateStep() {
       throw new UnsupportedOperationException();
    }
}
