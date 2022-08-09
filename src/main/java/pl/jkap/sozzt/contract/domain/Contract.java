package pl.jkap.sozzt.contract.domain;


interface Contract {

    Contract confirmStep();

    ContractEntity toContractEntity();
}
