package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

class InMemoryContractRepository extends InMemoryRepository<Contract, UUID> implements ContractRepository {

    @Override
    protected <S extends Contract> UUID getIdFromEntity(S entity) {
        return entity.getContractId();
    }

}
