package pl.jkap.sozzt.contract.domain;


import org.hibernate.cfg.NotYetImplementedException;

class WaitingToPreliminaryMapStep implements ContractStep {

    private final Contract contract;

    WaitingToPreliminaryMapStep(Contract contract) {
        this.contract = contract;
    }

    @Override
    public void confirmStep() {
        throw new NotYetImplementedException();
    }
}
