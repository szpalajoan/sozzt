package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.contract.dto.ContractDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;

    private ContractStep contractStep;

    void setContractStep(ContractStep contractStep) {
        this.contractStep = contractStep;
    }

    void changeState(ContractStep contractStep){
        this.contractStep = contractStep;
    }

    void confirmStep(){
        this.contractStep.confirmStep();
    }

    ContractDTO dto(){
        return ContractDTO.builder()
                .id(id)
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .build();
    }



}


