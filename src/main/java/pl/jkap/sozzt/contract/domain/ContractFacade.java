package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.filestorage.event.ContractScanDeletedEvent;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade;
import pl.jkap.sozzt.contractsecurity.dto.AddSecurityContractDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Slf4j
@Builder
public class ContractFacade {

    private final ContractSecurityFacade contractSecurityFacade;
    private final ContractRepository contractRepository;
    private final ContractCreator contractCreator;
    private final ContractStepCreator contractStepCreator;
    private final InstantProvider instantProvider;

    public ContractDto addContract(CreateContractDto createContractDto) {
        requireNonNull(createContractDto);
        contractSecurityFacade.checkCanAddContract();
        Contract contract = contractCreator.from(createContractDto);
        contract = contractRepository.save(contract);
        ContractDto addedContract = contract.dto();
        contractSecurityFacade.addSecurityContract(new AddSecurityContractDto(contract.getContractId()));
        log.info("Contract created: {}", addedContract);
        return addedContract;
    }

    public ContractDto getContract(UUID id) {
        return findContract(id).dto();
    }

    public List<ContractDto> getContracts() {
        Iterable<Contract> contracts = contractRepository.findAll();
        List<ContractDto> contractDtos = new ArrayList<>();
        contracts.forEach(contract -> contractDtos.add(contract.dto()));
        return contractDtos;
    }


    public ContractDto finalizeIntroduction(UUID contractId) {
        Contract contract = findContract(contractId);
        contract.createSteps(contractStepCreator);
        return null;
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
