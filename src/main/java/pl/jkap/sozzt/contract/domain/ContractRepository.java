package pl.jkap.sozzt.contract.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface ContractRepository extends JpaRepository<ContractEntity, Long> {
}
