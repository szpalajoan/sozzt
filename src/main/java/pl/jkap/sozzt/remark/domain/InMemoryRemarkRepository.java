package pl.jkap.sozzt.remark.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.Collection;
import java.util.UUID;

class InMemoryRemarkRepository extends InMemoryRepository<Remark, UUID> implements RemarkRepository {
    @Override
    protected <S extends Remark> UUID getIdFromEntity(S entity) {
        return entity.getRemarkId();
    }

    @Override
    public Collection<Remark> findByContractIdAndRemarkContractStep(UUID contractId, RemarkContractStep remarkContractStep) {
        return table.values().stream()
                .filter(remark -> remark.getContractId().equals(contractId) && remark.getRemarkContractStep().equals(remarkContractStep))
                .toList();
    }
}
