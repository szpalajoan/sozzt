package pl.jkap.sozzt.remark.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.UUID;

interface RemarkRepository extends CrudRepository<Remark, UUID> {
    Collection<Remark> findByContractIdAndRemarkContractStep(UUID contractId, RemarkContractStep remarkContractStep);

    Collection<Remark> findByContractId(UUID contractId);
}
