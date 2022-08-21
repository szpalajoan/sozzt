package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.AddContractDto
import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum

import java.time.LocalDateTime

trait ContractSample {

    AddContractDto NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT = createNewContractDto("2022VOLTAGE", "Tarnów", "Electro")

    private static AddContractDto createNewContractDto(String invoiceNumber, String location, String executive) {
        return AddContractDto.builder()
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .build()
    }

    private static ContractDto createContractDto(UUID id, String invoiceNumber, String location, String executive, boolean isScanFromTauronUpload, boolean isPreliminaryMapUpload, ContractStepEnum contractStepEnum, LocalDateTime created) {
        return ContractDto.builder()
                .id(id)
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .isScanFromTauronUpload(isScanFromTauronUpload)
                .isPreliminaryMapUpload(isPreliminaryMapUpload)
                .contactStepEnum(contractStepEnum)
                .created(created)
                .build()
    }

}