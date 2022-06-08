package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.contract.dto.ContractDTO;
import pl.jkap.sozzt.contract.dto.DataInputContractDTO;

import static java.util.Objects.requireNonNull;

class ContractCreator {
    Contract from(ContractDTO ContractDTO){
        requireNonNull(ContractDTO);

        Contract contract = Contract.builder()
                .id(ContractDTO.getId())
                .invoiceNumber(ContractDTO.getInvoiceNumber())
                .location(ContractDTO.getLocation())
                .executive(ContractDTO.getExecutive())
                .build();

        contract.setContractStep(new DataInputStep(contract));
        
        return contract;
    }

}
