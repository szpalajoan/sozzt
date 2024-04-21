package pl.jkap.sozzt.contract.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


interface ContractRepository extends CrudRepository<Contract, UUID> {

}
