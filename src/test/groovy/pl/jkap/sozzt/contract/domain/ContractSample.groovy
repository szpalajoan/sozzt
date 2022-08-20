package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum

import java.time.LocalDateTime

trait ContractSample {

    ContractDto NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT = createContractDto(1, "2022VOLTAGE", "Tarnów", "Electro", false, false, ContractStepEnum.DATA_INPUT_STEP, LocalDateTime.now())

    private static ContractDto createContractDto(Long id, String invoiceNumber, String location, String executive, boolean isScanFromTauronUpload, boolean isPreliminaryMapUpload, ContractStepEnum contractStepEnum, LocalDateTime created) {
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