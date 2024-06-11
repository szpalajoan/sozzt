package pl.jkap.sozzt.contractsecurity.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface ContractSecurityRepository extends CrudRepository<SecurityContract, UUID> {
}
