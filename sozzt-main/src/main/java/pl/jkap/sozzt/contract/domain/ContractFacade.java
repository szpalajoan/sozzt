package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.fileContract.event.FileUploadedSpringEvent;
import pl.jkap.sozzt.instant.InstantProvider;

import static java.util.Objects.requireNonNull;

@Builder
public class ContractFacade {

    private final ContractRepository contractRepository;
    private final ContractCreator contractCreator;
    private final InstantProvider instantProvider;

    public ContractDto addContract(ContractDto contractDTO) {
        requireNonNull(contractDTO);
        Contract contract = contractCreator.from(contractDTO);
        contract = contractRepository.save(contract);
        return contract.dto();
    }

    public ContractDto getContract(long id) {
        return contractRepository.findById(id).dto();
    }

    @EventListener
    public void uploadedScanFromTauron(FileUploadedSpringEvent event) {
        confirmScanUploaded(event.getMessage());
    }

    public ContractDto confirmStep(long idContract) {
        Contract contract = contractRepository.findById(idContract);
        contract.confirmStep();
        return contractRepository.save(contract).dto();
    }

    public void confirmScanUploaded(long idContract) {
        Contract contract = contractRepository.findById(idContract);
        contract.confirmScanUploaded();
        contractRepository.save(contract);

    }
    public boolean checkIsScanFromTauronUploaded(long idContract){
        Contract contract = contractRepository.findById(idContract);
        return contract.checkIsScanFromTauronUploaded();
    }




}
