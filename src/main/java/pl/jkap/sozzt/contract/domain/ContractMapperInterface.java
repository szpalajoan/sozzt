package pl.jkap.sozzt.contract.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ContractMapperInterface {

    ContractData map(ContractEntity contractEntity);

    @Mapping(target = "contractData", source = "contractEntity")
    DataInputContract dataInputStepFrom(ContractEntity contractEntity);

    @Mapping(target = "contractData", source = "contractEntity")
    PreliminaryMapToUploadContract preliminaryMapToUploadStepFrom(ContractEntity contractEntity);

    @Mapping(target = "contractData", source = "contractEntity")
    PreliminaryMapToVerifyContract preliminaryMapToVerifyStepFrom(ContractEntity contractEntity);

    @Mapping(target = "contractData", source = "contractEntity")
    ListOfConsentsToAddContract listOfConsentsToAddContractStepFrom(ContractEntity contractEntity);

    @Mapping(target = "contractData", source = "contractEntity")
    ConsentsToAcceptContract consentsToAcceptContractStepFrom(ContractEntity contractEntity);
}