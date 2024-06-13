package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Embeddable;

@Embeddable
class ContractProgress {

    private ContractStep contractStep;
}
