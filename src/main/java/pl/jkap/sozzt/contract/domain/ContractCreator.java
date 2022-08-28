package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class ContractCreator {

    DataInputContract createContract(AddContractDto addContractDto) {
        requireNonNull(addContractDto);
        return DataInputContract.builder()
                .contractData(buildContractData(addContractDto))
                .isScanFromTauronUpload(false)
                .build();
    }

    private ContractData buildContractData(AddContractDto addContractDto) {
        return ContractData.builder()
                .id(UUID.randomUUID())
                .executive(addContractDto.getExecutive())
                .location(addContractDto.getLocation())
                .contactStepEnum(ContractStepEnum.DATA_INPUT_STEP)
                .invoiceNumber(addContractDto.getInvoiceNumber())
                .created(LocalDateTime.now())
                .build();
    }

    public ContractEntity createContactEntity(DataInputContract dataInputContract) {
        return ContractEntity.builder()
                .id(dataInputContract.getContractData().getId())
                .executive(dataInputContract.getContractData().getExecutive())
                .location(dataInputContract.getContractData().getLocation())
                .contractStepEnum(dataInputContract.getContractData().getContactStepEnum())
                .invoiceNumber(dataInputContract.getContractData().getInvoiceNumber())
                .scanFromTauronUpload(dataInputContract.isScanFromTauronUpload())
                .created(LocalDateTime.now())
                .build();
    }
}

