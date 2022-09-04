package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.cfg.NotYetImplementedException;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;
import pl.jkap.sozzt.contract.exception.NotAllConsentsAreConfirmedException;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.CONSENTS_TO_ACCEPT;

@Builder
@Getter
class ConsentsToAcceptContract implements Contract {

    private final ContractData contractData;
    private boolean allConsentAccepted;

    ConsentsToAcceptContract(ContractData contractData, boolean allConsentAccepted) {
        this.contractData = contractData;
        this.contractData.setContactStepEnum(CONSENTS_TO_ACCEPT);
        this.allConsentAccepted = allConsentAccepted;
    }

    void setAllConsentAccepted() {
        allConsentAccepted = true;
    }

    @Override
    public MapUnderTheLawUploadContract confirmStep() {
        if (allConsentAccepted) {
            return new MapUnderTheLawUploadContract(this.contractData);
        } else {
            throw new NotAllConsentsAreConfirmedException("Not all consents are confirmed.");
        }
    }

    @Override
    public PreliminaryMapToUploadContract withdrawalToNewPreliminaryMapUpload() {
        return new PreliminaryMapToUploadContract(contractData, false);
    }

    @Override
    public ContractData getContractData() {
        return contractData;
    }

    @Override
    public ContractDataDto dto() {
        return ContractDataDto.builder()
                .id(contractData.getId())
                .invoiceNumber(contractData.getInvoiceNumber())
                .location(contractData.getLocation())
                .executive(contractData.getExecutive())
                .created(contractData.getCreated())
                .contactStepEnum(contractData.getContactStepEnum())
                .build();
    }
}
