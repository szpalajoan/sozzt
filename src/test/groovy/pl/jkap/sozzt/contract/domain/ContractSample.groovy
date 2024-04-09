package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.SampleModifier
import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.CreateContractDto
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.user.UserSample

trait ContractSample implements UserSample, InstantSamples {

    ContractDto MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT = createContractDto(UUID.fromString("21c4aaa0-4a11-4f83-aa2e-504e23d14495"),
            "2022VOLTAGE",
            "Tarnów")

    private ContractDto createContractDto(UUID id, String invoiceNumber, String location){
        return ContractDto.builder()
        .id(id)
        .invoiceNumber(invoiceNumber)
        .location(location)
        .createdBy(MONIKA_CONTRACT_INTRODUCER.name)
        .createdAt(NOW)
        .build()
    }

    ContractDto with(ContractDto contractDto, Map<String, Object> properties) {
        return SampleModifier.with(ContractDto.class, contractDto, properties)
    }

    CreateContractDto createdContract(ContractDto contractDto) {
        return CreateContractDto.builder()
                .id(contractDto.id)
                .invoiceNumber(contractDto.invoiceNumber)
                .location(contractDto.location)
                .build()
    }
}