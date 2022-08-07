package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
class ContractCreator {

    ContractEntity from(ContractDto ContractDTO) {
        requireNonNull(ContractDTO);

        return ContractEntity.builder()
                .id(ContractDTO.getId())
                .invoiceNumber(ContractDTO.getInvoiceNumber())
                .location(ContractDTO.getLocation())
                .executive(ContractDTO.getExecutive())
                .scanFromTauronUpload(ContractDTO.isScanFromTauronUpload())
                .contractStepEnum(ContractDTO.getContactStepEnum())
                .created(ContractDTO.getCreated())
                .build();
    }

    Contract from(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        Contract contract = Contract.builder()
                .id(contractEntity.getId())
                .invoiceNumber(contractEntity.getInvoiceNumber())
                .location(contractEntity.getLocation())
                .executive(contractEntity.getExecutive())
                .created(contractEntity.getCreated())
                .build();

        if (contractEntity.getContractStepEnum() == ContractStepEnum.DATA_INPUT_STEP) {
            contract.setContractStep(new DataInputStep(contract, contractEntity.isScanFromTauronUpload()));
        } else if (contractEntity.getContractStepEnum() == ContractStepEnum.WAITING_TO_PRELIMINARY_MAP_STEP) {
            contract.setContractStep(new WaitingToPreliminaryMapStep(contract));
        }
        return contract;
    }

}
