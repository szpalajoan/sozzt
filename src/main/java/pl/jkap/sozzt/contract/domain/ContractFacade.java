package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;
import pl.jkap.sozzt.filestorage.event.FileUploadedEvent;
import pl.jkap.sozzt.instant.InstantProvider;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Builder
public class ContractFacade {

    private final ContractRepository contractRepository;
    private final ContractCreator contractCreator;
    private final InstantProvider instantProvider;

    public ContractDto addContract(CreateContractDto createContractDto) {
        requireNonNull(createContractDto);
        Contract contract = contractCreator.from(createContractDto);
        contract = contractRepository.save(contract);
        return contract.dto();
    }

    public ContractDto getContract(UUID id) {
        return contractRepository.findById(id).dto();
    }

    @EventListener
    public void uploadedScanFromTauron(FileUploadedEvent event) {
        confirmScanUploaded(event.getMessage());
    }

    public ContractDto confirmStep(UUID idContract) {
        Contract contract = contractRepository.findById(idContract);
        contract.confirmStep();
        return contractRepository.save(contract).dto();
    }

    public void confirmScanUploaded(UUID idContract) {
        Contract contract = contractRepository.findById(idContract);
        contract.confirmScanUploaded();
        contractRepository.save(contract);

    }
    public boolean checkIsScanFromTauronUploaded(UUID idContract){
        Contract contract = contractRepository.findById(idContract);
        return contract.checkIsScanFromTauronUploaded();
    }




}
