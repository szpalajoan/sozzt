package pl.jkap.sozzt.contract.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.jkap.sozzt.contract.dto.ContractDtoRepository;

import java.util.UUID;

interface ContractRepository extends JpaRepository<ContractEntity, UUID> {

    @Query(value = "select c.id as id, c.invoiceNumber as invoiceNumber, c.location as location, " +
            "c.executive as executive, c.scanFromTauronUpload as scanFromTauronUpload," +
            "c.preliminaryMapUpload as preliminaryMapUpload, " +
            "c.allConsentAccepted as allConsentAccepted, " +
            "c.contractStepEnum as contractStepEnum, " +
            "c.created as created  from ContractEntity c where c.id = :id ")
    ContractDtoRepository findContractDataById(@Param("id") UUID searchValue);
}

