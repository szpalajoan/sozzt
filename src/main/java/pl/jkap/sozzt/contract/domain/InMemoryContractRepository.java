package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.utils.InMemoryRepository;


class InMemoryContractRepository extends InMemoryRepository<ContractEntity, Long> implements ContractRepository {

    @Override
    public Long getId(ContractEntity entity) {
        return entity.getId();
    }
}
