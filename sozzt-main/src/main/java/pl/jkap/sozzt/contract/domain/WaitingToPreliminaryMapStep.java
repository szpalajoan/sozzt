package pl.jkap.sozzt.contract.domain;


class WaitingToPreliminaryMapStep implements ContractStep {

    private final Contract contract;

    WaitingToPreliminaryMapStep(Contract contract) {
        this.contract = contract;
    }

    @Override
    public void confirmStep() {

    }
}
