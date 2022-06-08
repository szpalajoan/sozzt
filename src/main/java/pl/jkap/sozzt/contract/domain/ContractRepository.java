package pl.jkap.sozzt.contract.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

interface ContractRepository extends Repository<Contract, Long> {

    Contract save(Contract contract);

    Contract findById(Long id);

    void deleteById(Long id);

    Page<Contract> findAll(Pageable pageable);
}
