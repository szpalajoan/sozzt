package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto

trait ContractSample {

    ContractDto MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT = createContractDto(1, "2022VOLTAGE","Tarnów","Electro")

    private static ContractDto createContractDto(Long id, String invoiceNumber, String location, String executive){
        return ContractDto.builder()
        .id(id)
        .invoiceNumber(invoiceNumber)
        .location(location)
        .executive(executive)
        .build()
    }

}