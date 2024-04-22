package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.filestorage.event.ContractScanDeletedEvent;
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
        return findContract(id).dto();
    }


    public ContractDto confirmStep(UUID idContract) {
        Contract contract = findContract(idContract);
        contract.confirmStep();
        return contractRepository.save(contract).dto();
    }

    public void scanUploaded(UUID contractId) {
        Contract contract = findContract(contractId);
        contract.confirmScanUploaded();
        contractRepository.save(contract);
    }

    private void scanDeleted(UUID contractId) {
        Contract contract = findContract(contractId);
        contract.scanDeleted();
        contractRepository.save(contract);
    }

    private Contract findContract(UUID id) {
        return contractRepository.findById(id).orElseThrow(() -> new ContractNotFoundException("Contract not found: " + id));
    }

    @EventListener
    public void onContractScanAddedEvent(ContractScanAddedEvent event) {
        scanUploaded(event.getContractId());
    }

    @EventListener
    public void onContractScanDeletedEvent(ContractScanDeletedEvent event) {
        scanDeleted(event.getContractId());
    }

}
