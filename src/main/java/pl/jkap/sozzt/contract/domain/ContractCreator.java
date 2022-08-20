package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class ContractCreator {

    ContractEntity createContract(AddContractDto addContractDto) {
        requireNonNull(addContractDto);

        return ContractEntity.builder()
                .id(UUID.randomUUID())
                .executive(addContractDto.getExecutive())
                .location(addContractDto.getLocation())
                .invoiceNumber(addContractDto.getInvoiceNumber())
                .isScanFromTauronUpload(false)
                .isPreliminaryMapUpload(false)
                .contractStepEnum(ContractStepEnum.DATA_INPUT_STEP)
                .created(LocalDateTime.now())
                .build();
    }
}
