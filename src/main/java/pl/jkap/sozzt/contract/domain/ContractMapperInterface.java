package pl.jkap.sozzt.contract.domain;

import org.mapstruct.Mapper;

@Mapper
public interface ContractMapperInterface {
    DataInputContract dataInputStepFrom(ContractEntity contractEntity);
}
