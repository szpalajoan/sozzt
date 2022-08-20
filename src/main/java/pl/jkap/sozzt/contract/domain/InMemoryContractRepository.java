package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.utils.InMemoryRepository;

import java.util.UUID;

import static java.util.Objects.requireNonNull;


class InMemoryContractRepository extends InMemoryRepository<ContractEntity, UUID> implements ContractRepository {

    @Override
    public UUID getId(ContractEntity contractEntity) {
        return contractEntity.getId();
    }

    @Override
    public ContractEntity save(ContractEntity entity) {
        requireNonNull(entity);
        map.put(getId(entity), entity);
        return entity;
    }

}
