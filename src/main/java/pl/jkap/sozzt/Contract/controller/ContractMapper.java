package pl.jkap.sozzt.Contract.controller;

import pl.jkap.sozzt.Contract.controller.dto.ContractDTO;
import pl.jkap.sozzt.Contract.model.Contract;

import java.util.List;
import java.util.stream.Collectors;

public class ContractMapper {


    private ContractMapper() {
    }

    public static List<ContractDTO> mapToContractDtos(List<Contract> contracts) {
        return contracts.stream()
                .map(ContractMapper::mapToContractDto)
                .collect(Collectors.toList());
    }

    public static ContractDTO mapToContractDto(Contract contract) {
        return ContractDTO.builder()
                .id_contract_basic_data(contract.getId())
                .created(contract.getCreated())
                .executive(contract.getExecutive())
                .invoice_number(contract.getInvoiceNumber())
                .location(contract.getLocation())
                .build();
    }
}
