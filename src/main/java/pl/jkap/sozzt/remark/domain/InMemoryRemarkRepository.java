package pl.jkap.sozzt.remark.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;

class InMemoryRemarkRepository extends InMemoryRepository<Remark, UUID> implements RemarkRepository {
    @Override
    protected <S extends Remark> UUID getIdFromEntity(S entity) {
        return entity.getRemarkId();
    }

    @Override
    public Collection<Remark> findByContractIdAndRemarkContractStep(UUID contractId, RemarkType remarkType) {
        return table.values().stream()
                .filter(remark -> remark.getContractId().equals(contractId) && remark.getRemarkType().equals(remarkType))
                .sorted(Comparator.comparing(Remark::getDeadline))
                .toList();
    }

    @Override
    public Collection<Remark> findByContractId(UUID contractId) {
        return table.values().stream()
                .filter(remark -> remark.getContractId().equals(contractId))
                .sorted(Comparator.comparing(Remark::getDeadline))
                .toList();
    }
}
