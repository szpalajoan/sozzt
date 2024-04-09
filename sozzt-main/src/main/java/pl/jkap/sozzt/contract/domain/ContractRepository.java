package pl.jkap.sozzt.contract.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.UUID;


interface ContractRepository extends Repository<Contract, UUID> {

    Contract save(Contract contract);

    Contract findById(UUID id);

    void deleteById(UUID id);

    Page<Contract> findAll(Pageable pageable);
}
