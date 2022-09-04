package pl.jkap.sozzt.contract.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ContractDtoRepository {
    UUID getId();

    String getInvoiceNumber();

    String getLocation();

    String getExecutive();

    boolean getScanFromTauronUpload();

    boolean getPreliminaryMapUpload();

    boolean getAllConsentAccepted();

    String getContractStepEnum();

    LocalDateTime getCreated();

}



