package pl.jkap.sozzt.contract.domain;

import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.fileContract.event.FileUploadedSpringEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ContractFacade {

    private static final int PAGE_SIZE = 5;
    private final ContractRepository contractRepository;
    private final ContractCreator contractCreator;

    public ContractFacade(ContractRepository contractRepository, ContractCreator contractCreator) {
        this.contractCreator = contractCreator;
        this.contractRepository = contractRepository;
    }

    public ContractDto addContract(ContractDto contractDTO) {
        requireNonNull(contractDTO);
        ContractEntity contractEntity = contractCreator.from(contractDTO);
        Contract contract = contractCreator.from(contractEntity);
        contract.setContractStep(new DataInputStep(contract, false));
        contract.setCreated(LocalDateTime.now());
        return contractRepository.save(contract.toContractEntity()).dto();
    }

    public ContractDto getContract(long id) {
        return contractRepository.findById(id).orElseThrow(ContractNotFoundException::new).dto();
    }

    @EventListener
    public void uploadedScanFromTauron(FileUploadedSpringEvent fileUploadedSpringEvent) {
        confirmScanUploaded(fileUploadedSpringEvent.getIdContract());
    }

    public ContractDto confirmStep(long idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract).orElseThrow(ContractNotFoundException::new);
        Contract contract = contractCreator.from(contractEntity);
        contract.confirmStep();
        return contractRepository.save(contract.toContractEntity()).dto();
    }

    public void confirmScanUploaded(long idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract).orElseThrow(ContractNotFoundException::new);
        Contract contract = contractCreator.from(contractEntity);
        contract.confirmScanUploaded();
        contractRepository.save(contract.toContractEntity());
    }

    public List<ContractDto> getContracts(int page) {
        return contractRepository.findAll(PageRequest.of(page, PAGE_SIZE)).stream().map(ContractEntity::dto).collect(Collectors.toList());
    }

}
