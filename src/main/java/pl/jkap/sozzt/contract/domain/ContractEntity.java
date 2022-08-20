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