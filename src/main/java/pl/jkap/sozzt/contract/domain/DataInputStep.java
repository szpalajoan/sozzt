package pl.jkap.sozzt.contract.domain;

import javax.persistence.Entity;

@Entity
class DataInputStep implements ContractStep{

    private final Contract contract;

    DataInputStep(Contract contract) {
        this.contract = contract;
    }

    @Override
    public void confirmStep() {
        this.contract.changeState(new WaitingToPreliminaryMapStep(this.contract));
    }
}


