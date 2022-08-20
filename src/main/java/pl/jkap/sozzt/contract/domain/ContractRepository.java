package pl.jkap.sozzt.contract.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ContractRepository extends JpaRepository<ContractEntity, UUID> {
}
