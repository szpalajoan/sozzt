package pl.jkap.sozzt.contract.domain;


import lombok.*;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Contract")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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