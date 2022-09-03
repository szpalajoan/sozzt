package pl.jkap.sozzt.contract.domain;

import org.hibernate.cfg.NotYetImplementedException;
import org.mapstruct.factory.Mappers;

import static java.util.Objects.requireNonNull;


class ContractMapper {

    //    DataInputContract dataInputStepFrom(ContractEntity contractEntity) {
//        return DataInputContract.builder()
//                .contractData(getContractData(contractEntity))
//                .isScanFromTauronUpload(contractEntity.isScanFromTauronUpload())
//                .build();
//    }
    private final ContractMapperInterface mapper = Mappers.getMapper(ContractMapperInterface.class);

    PreliminaryMapToUploadContract preliminaryMapToUploadStepFrom(ContractEntity contractEntity) {
        return PreliminaryMapToUploadContract.builder()
                .contractData(getContractData(contractEntity))
                .isPreliminaryMapUpload(contractEntity.isPreliminaryMapUpload())
                .build();
    }

    PreliminaryMapToVerifyContract preliminaryMapToVerifyStepFrom(ContractEntity contractEntity) {
        return PreliminaryMapToVerifyContract.builder().contractData(getContractData(contractEntity)).build();
    }

    private Contract listOfConsentsToAddContractStepFrom(ContractEntity contractEntity) {
        return ListOfConsentsToAddContract.builder().contractData(getContractData(contractEntity)).build();
    }

    private Contract consentsToAcceptContractStepFrom(ContractEntity contractEntity) {
        return ConsentsToAcceptContract.builder().contractData(getContractData(contractEntity)).build();

    }

    private ContractData getContractData(ContractEntity contractEntity) {
        return ContractData.builder()
                .id(contractEntity.getId())
                .invoiceNumber(contractEntity.getInvoiceNumber())
                .location(contractEntity.getLocation())
                .executive(contractEntity.getExecutive())
                .created(contractEntity.getCreated())
                .contactStepEnum(contractEntity.getContractStepEnum())
                .build();
    }

    public Contract from(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        switch (contractEntity.getContractStepEnum()) {
            case DATA_INPUT: {
                return mapper.dataInputStepFrom(contractEntity);
            }
            case PRELIMINARY_MAP_TO_UPLOAD: {
                return preliminaryMapToUploadStepFrom(contractEntity);
            }
            case PRELIMINARY_MAP_TO_VERIFY: {
                return preliminaryMapToVerifyStepFrom(contractEntity);
            }
            case LIST_OF_CONSENTS_TO_ADD: {
                return listOfConsentsToAddContractStepFrom(contractEntity);
            }
            case CONSENTS_TO_ACCEPT: {
                return consentsToAcceptContractStepFrom(contractEntity);
            }
            default: {
                throw new NotYetImplementedException();
            }
        }
    }
}
