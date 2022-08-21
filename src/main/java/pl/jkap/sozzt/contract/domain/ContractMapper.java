package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
class ContractMapper {

    DataInputContract dataInputStepFrom(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        return DataInputContract.builder()
                .contractData(getContractData(contractEntity))
                .isScanFromTauronUpload(contractEntity.isScanFromTauronUpload())
                .build();
    }

    PreliminaryMapToUploadContract preliminaryMapToUploadStepFrom(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        return PreliminaryMapToUploadContract.builder()
                .contractData(getContractData(contractEntity))
                .isPreliminaryMapUpload(contractEntity.isPreliminaryMapUpload())
                .build();
    }

    PreliminaryMapToVerifyContract preliminaryMapToVerifyStepFrom(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        return PreliminaryMapToVerifyContract.builder()
                .contractData(getContractData(contractEntity))
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
        } else if (contractEntity.getContractStepEnum().equals(ContractStepEnum.PRELIMINARY_MAP_TO_VERIFY)) {
            return preliminaryMapToVerifyStepFrom(contractEntity);
        } else {
            throw new NotYetImplementedException();
        }
    }
}
