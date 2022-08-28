package pl.jkap.sozzt.contract.domain;


import pl.jkap.sozzt.contract.dto.ContractDataDto;

interface Contract {

    Contract confirmStep();

    ContractData getContractData();

    ContractDataDto dto();
}
