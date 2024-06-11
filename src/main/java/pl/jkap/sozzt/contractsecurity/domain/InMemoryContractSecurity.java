package pl.jkap.sozzt.contractsecurity.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

class InMemoryContractSecurity extends InMemoryRepository<SecurityContract, UUID> implements ContractSecurityRepository {

    @Override
    protected <S extends SecurityContract> UUID getIdFromEntity(S entity) {
        return entity.getSecurityContractId();
    }
}
