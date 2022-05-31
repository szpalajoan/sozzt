package pl.jkap.sozzt.contract.service.stepContract;

import pl.jkap.sozzt.contract.model.ContractStep;

public class WaitingToPreliminaryStep implements Step {
    @Override
    public ContractStep getFileType() {
        return ContractStep.WAITING_TO_PRELIMINARY;
    }

    @Override
    public Step validateStep() {
       throw new UnsupportedOperationException();
    }
}
