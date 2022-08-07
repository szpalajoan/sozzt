package pl.jkap.sozzt.contract.domain;


import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "Contract")
@Getter
@Builder
class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;

    @Column(name = "contract_step")
    private ContractStepEnum contractStepEnum;

    @Column(name = "is_scan_from_tauron_upload")
    private boolean scanFromTauronUpload;

    ContractDto dto() {
        return ContractDto.builder()
                .id(id)
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .scanFromTauronUpload(scanFromTauronUpload)
                .contactStepEnum(contractStepEnum)
                .created(created)
                .build();
    }
}
