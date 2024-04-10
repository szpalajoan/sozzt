package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDetailsDto

trait ContractDetailsSample {
    ContractDetailsDto KRYNICA_CONTRACT_DETAILS = ContractDetailsDto.builder()
            .contractNumber("012172/2024/O09R08/2401965")
            .workNumber("2401965")
            .customerContractNumber("WP/012172/2024/O09R08")
            .build()

}