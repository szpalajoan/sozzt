package pl.jkap.sozzt.contract.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Contract")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ContractEntity {

    @Id
    private UUID id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;

    @Column(name = "contract_step")
    @Enumerated(EnumType.STRING)
    private ContractStepEnum contractStepEnum;

    private boolean scanFromTauronUpload;
    private boolean preliminaryMapUpload;
    private boolean allConsentAccepted;

    void updateFromContract(Contract contract) {
        setContractData(contract.getContractData());
    }

    void setContractData(ContractData contractData) {
        this.id = contractData.getId();
        this.invoiceNumber = contractData.getInvoiceNumber();
        this.location = contractData.getLocation();
        this.executive = contractData.getExecutive();
        this.created = contractData.getCreated();
        this.contractStepEnum = contractData.getContactStepEnum();
    }

    public void update(DataInputContract dataInputContract) {
        setContractData(dataInputContract.getContractData());
        scanFromTauronUpload = dataInputContract.isScanFromTauronUpload();
    }

    public void update(PreliminaryMapToUploadContract preliminaryMapToUploadContract) {
        setContractData(preliminaryMapToUploadContract.getContractData());
        preliminaryMapUpload = preliminaryMapToUploadContract.isPreliminaryMapUpload();
    }

    public void update(ConsentsToAcceptContract consentsToAcceptContract) {
        setContractData(consentsToAcceptContract.getContractData());
        allConsentAccepted = consentsToAcceptContract.isAllConsentAccepted();
    }

}