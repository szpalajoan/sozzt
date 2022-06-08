package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDTO

trait ContractSample {

    ContractDTO MEDIUM_VOLTAG_NETWORK_IN_TARNOW = createContractDto(1, "123","trol","exe")

    private static ContractDTO createContractDto(Long id, String invoiceNumber, String location, String executive){
        return ContractDTO.builder()
        .id(id)
        .invoiceNumber(invoiceNumber)
        .location(location)
        .executive(executive)
        .build()
    }
}