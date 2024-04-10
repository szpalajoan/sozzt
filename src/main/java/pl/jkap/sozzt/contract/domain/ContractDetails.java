package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.contract.dto.ContractDetailsDto;

final class ContractDetails {
    private final String contractNumber;
    private final String workNumber;
    private final String customerContractNumber;

    ContractDetails(ContractDetailsDto contractDetailsDto) {
        if (contractDetailsDto.getContractNumber() == null || contractDetailsDto.getContractNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Order number cannot be null or empty.");
        }
        if (contractDetailsDto.getWorkNumber() == null || contractDetailsDto.getWorkNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Work number cannot be null or empty.");
        }
        if (contractDetailsDto.getCustomerContractNumber() == null || contractDetailsDto.getCustomerContractNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer contract number cannot be null or empty.");
        }
        this.contractNumber = contractDetailsDto.getContractNumber();
        this.workNumber = contractDetailsDto.getWorkNumber();
        this.customerContractNumber = contractDetailsDto.getCustomerContractNumber();
    }

    ContractDetailsDto dto() {
        return ContractDetailsDto.builder()
                .contractNumber(this.contractNumber)
                .workNumber(this.workNumber)
                .customerContractNumber(this.customerContractNumber)
                .build();
    }
}
