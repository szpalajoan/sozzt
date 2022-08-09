package pl.jkap.sozzt.contract.domain;

import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.fileContract.event.FileUploadedSpringEvent;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ContractFacade {

    private static final int PAGE_SIZE = 5;
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    public ContractFacade(ContractRepository contractRepository, ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
        this.contractRepository = contractRepository;
    }

    public ContractDto addContract(ContractDto contractDTO) {
        requireNonNull(contractDTO);
        ContractEntity contractEntity = contractMapper.from(contractDTO);
        DataInputContract dataInputContract = contractMapper.dataInputStepFrom(contractEntity);
        return contractRepository.save(dataInputContract.toContractEntity()).dto();
    }

    public ContractDto getContract(long id) {
        return contractRepository.findById(id).orElseThrow(ContractNotFoundException::new).dto();
    }

    @EventListener
    public void uploadedScanFromTauron(FileUploadedSpringEvent fileUploadedSpringEvent) {
        confirmScanUploaded(fileUploadedSpringEvent.getIdContract());
    }

    public void confirmScanUploaded(long idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract).orElseThrow(ContractNotFoundException::new);
        DataInputContract dataInputContract = contractMapper.dataInputStepFrom(contractEntity);
        dataInputContract.setScanFromTauronUpload(true);
        contractRepository.save(dataInputContract.toContractEntity());
    }

    public ContractDto confirmStep(long idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract).orElseThrow(ContractNotFoundException::new);
        Contract contract = contractMapper.from(contractEntity);
        Contract confirmContract = contract.confirmStep();
        contractEntity.setContractStepEnum(confirmContract.toContractEntity().getContractStepEnum());
        return contractRepository.save(contractEntity).dto();
    }

    public List<ContractDto> getContracts(int page) {
        return contractRepository.findAll(PageRequest.of(page, PAGE_SIZE)).stream().map(ContractEntity::dto).collect(Collectors.toList());
    }

}
