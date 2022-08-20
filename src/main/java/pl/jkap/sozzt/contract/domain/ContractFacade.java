package pl.jkap.sozzt.contract.domain;

import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.fileContract.event.UploadedPreliminaryMapSpringEvent;
import pl.jkap.sozzt.fileContract.event.UploadedScanFromTauronSpringEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ContractFacade {

    private static final int PAGE_SIZE = 5;
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final ContractCreator contractCreator;

    public ContractFacade(ContractRepository contractRepository, ContractMapper contractMapper, ContractCreator contractCreator) {
        this.contractMapper = contractMapper;
        this.contractRepository = contractRepository;
        this.contractCreator = contractCreator;
    }

    public ContractDto addContract(AddContractDto addContractDto) {
        requireNonNull(addContractDto);
        ContractEntity contractEntity = contractCreator.createContract(addContractDto);
        return contractRepository.save(contractEntity).dto();
    }

    public ContractDto getContract(UUID id) {
        return contractRepository.findById(id).orElseThrow(ContractNotFoundException::new).dto();
    }

    @EventListener
    public void uploadedScanFromTauron(UploadedScanFromTauronSpringEvent uploadedScanFromTauronSpringEvent) {
        confirmScanUploaded(uploadedScanFromTauronSpringEvent.getIdContract());
    }

    public void confirmScanUploaded(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        DataInputContract dataInputContract = contractMapper.dataInputStepFrom(contractEntity);
        dataInputContract.setScanFromTauronUpload(true);
        dataInputContract.updateContractEntity(contractEntity);
        contractRepository.save(contractEntity).dto();
    }

    @EventListener
    public void uploadedPreliminaryMap(UploadedPreliminaryMapSpringEvent uploadedPreliminaryMapSpringEvent) {
        confirmPreliminaryMapUploaded(uploadedPreliminaryMapSpringEvent.getIdContract());
    }

    public void confirmPreliminaryMapUploaded(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        PreliminaryMapToUploadContract preliminaryMapToUploadContract = contractMapper.preliminaryMapToUploadStepFrom(contractEntity);
        preliminaryMapToUploadContract.setPreliminaryMapUpload(true);
        preliminaryMapToUploadContract.updateContractEntity(contractEntity);
        contractRepository.save(contractEntity);
    }


    public ContractDto confirmStep(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        Contract contract = contractMapper.from(contractEntity);
        Contract confirmContract = contract.confirmStep();
        confirmContract.updateContractEntity(contractEntity);
        return contractRepository.save(contractEntity).dto();
    }

    public List<ContractDto> getContracts(int page) {
        return contractRepository.findAll(PageRequest.of(page, PAGE_SIZE))
                .stream()
                .map(ContractEntity::dto)
                .collect(Collectors.toList());
    }

}
