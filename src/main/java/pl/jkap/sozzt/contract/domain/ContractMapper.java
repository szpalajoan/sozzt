package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
class ContractMapper {

    ContractEntity from(ContractDto ContractDTO) {
        requireNonNull(ContractDTO);

        return ContractEntity.builder()
                .id(ContractDTO.getId())
                .invoiceNumber(ContractDTO.getInvoiceNumber())
                .location(ContractDTO.getLocation())
                .executive(ContractDTO.getExecutive())
                .isScanFromTauronUpload(ContractDTO.isScanFromTauronUpload())
                .isPreliminaryMapUpload(ContractDTO.isPreliminaryMapUpload())
                .contractStepEnum(ContractDTO.getContactStepEnum())
                .created(ContractDTO.getCreated())
                .build();
    }

    ContractEntity from(AddContractDto addContractDto) {
        requireNonNull(addContractDto);

        return ContractEntity.builder()
                .executive(addContractDto.getExecutive())
                .location(addContractDto.getLocation())
                .invoiceNumber(addContractDto.getInvoiceNumber())
                .isScanFromTauronUpload(false)
                .isPreliminaryMapUpload(false)
                .contractStepEnum(ContractStepEnum.DATA_INPUT_STEP)
                .created(LocalDateTime.now())
                .build();
    }

    public DataInputContract dataInputStepFrom(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        return DataInputContract.builder()
                .contractData(getContractData(contractEntity))
                .isScanFromTauronUpload(contractEntity.isScanFromTauronUpload())
                .build();
    }

    public PreliminaryMapToUploadContract preliminaryMapToUploadStepFrom(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        return PreliminaryMapToUploadContract.builder()
                .contractData(getContractData(contractEntity))
                .isPreliminaryMapUpload(contractEntity.isPreliminaryMapUpload())
                .build();
    }

    private ContractData getContractData(ContractEntity contractEntity) {
        return ContractData.builder()
                .id(contractEntity.getId())
                .invoiceNumber(contractEntity.getInvoiceNumber())
                .location(contractEntity.getLocation())
                .executive(contractEntity.getExecutive())
                .created(contractEntity.getCreated())
                .build();
    }

    public Contract from(ContractEntity contractEntity) {
        if (contractEntity.getContractStepEnum().equals(ContractStepEnum.DATA_INPUT_STEP)) {
            return dataInputStepFrom(contractEntity);
        } else if (contractEntity.getContractStepEnum().equals(ContractStepEnum.PRELIMINARY_MAP_TO_UPLOAD)) {
            return preliminaryMapToUploadStepFrom(contractEntity);
        } else {
            throw new NotYetImplementedException();
        }
    }
}
