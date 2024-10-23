package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Embeddable;
import pl.jkap.sozzt.contract.dto.ContractDetailsDto;

import java.time.Instant;

@Embeddable
final class ContractDetails {
    private String contractNumber;
    private String workNumber;
    private String customerContractNumber;
    private Instant orderDate;

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
        if (contractDetailsDto.getOrderDate() == null) {
            throw new IllegalArgumentException("Order date cannot be null.");
        }
        this.contractNumber = contractDetailsDto.getContractNumber();
        this.workNumber = contractDetailsDto.getWorkNumber();
        this.customerContractNumber = contractDetailsDto.getCustomerContractNumber();
        this.orderDate = contractDetailsDto.getOrderDate();
    }

    ContractDetailsDto dto() {
        return ContractDetailsDto.builder()
                .contractNumber(this.contractNumber)
                .workNumber(this.workNumber)
                .customerContractNumber(this.customerContractNumber)
                .orderDate(this.orderDate)
                .build();
    }

    Instant getOrderDate() {
        return orderDate;
    }

    public void edit(ContractDetailsDto contractDetails) {
        this.contractNumber = contractDetails.getContractNumber();
        this.workNumber = contractDetails.getWorkNumber();
        this.customerContractNumber = contractDetails.getCustomerContractNumber();
        this.orderDate = contractDetails.getOrderDate();
    }
}
