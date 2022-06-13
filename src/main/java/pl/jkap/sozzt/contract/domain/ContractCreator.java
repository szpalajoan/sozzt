package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.contract.dto.ContractDto;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

class ContractCreator {
    Contract from(ContractDto ContractDTO){
        requireNonNull(ContractDTO);

        Contract contract = Contract.builder()
                .id(ContractDTO.getId())
                .invoiceNumber(ContractDTO.getInvoiceNumber())
                .location(ContractDTO.getLocation())
                .executive(ContractDTO.getExecutive())
                .created(LocalDateTime.now())
                .build();

        contract.changeStep(new DataInputStep(contract));

        return contract;
    }

}
