package pl.jkap.sozzt.contract.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Contract")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ContractEntity {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;

    @Column(name = "contract_step")
    @Enumerated(EnumType.STRING)
    private ContractStepEnum contractStepEnum;

    private boolean isScanFromTauronUpload;
    private boolean isPreliminaryMapUpload;

    ContractDto dto() {
        return ContractDto.builder()
                .id(id)
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .isScanFromTauronUpload(isScanFromTauronUpload)
                .isPreliminaryMapUpload(isPreliminaryMapUpload)
                .contactStepEnum(contractStepEnum)
                .created(created)
                .build();
    }

    void setContractData(ContractData contractData) {
        this.id = contractData.getId();
        this.invoiceNumber = contractData.getInvoiceNumber();
        this.location = contractData.getLocation();
        this.executive = contractData.getExecutive();
        this.created = contractData.getCreated();
    }

}