package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.dto.ContractDtoRepository;
import pl.jkap.sozzt.contract.dto.DataInputContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.fileContract.event.PreliminaryMapUploadedSpringEvent;
import pl.jkap.sozzt.fileContract.event.ScanFromTauronUploadedSpringEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class ContractFacade {

    private static final int PAGE_SIZE = 5;
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final ContractCreator contractCreator;

    public DataInputContractDto addContract(AddContractDto addContractDto) {
        requireNonNull(addContractDto);
        DataInputContract dataInputContract = contractCreator.createContract(addContractDto);
        ContractEntity contractEntity = contractCreator.createContactEntity(dataInputContract);
        contractRepository.save(contractEntity);
        return dataInputContract.dataInputContractDto();
    }

    public ContractDtoRepository getContract(UUID id) {
        return contractRepository.findContractDataById(id);
    }

    @EventListener
    public void uploadedScanFromTauron(ScanFromTauronUploadedSpringEvent scanFromTauronUploadedSpringEvent) {
        confirmScanUploaded(scanFromTauronUploadedSpringEvent.getIdContract());
    }

    public void confirmScanUploaded(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        DataInputContract dataInputContract = contractMapper.dataInputStepFrom(contractEntity);
        dataInputContract.setScanFromTauronUpload(true);
        contractEntity.update(dataInputContract);
        contractRepository.save(contractEntity);
    }

    @EventListener
    public void uploadedPreliminaryMap(PreliminaryMapUploadedSpringEvent preliminaryMapUploadedSpringEvent) {
        confirmPreliminaryMapUploaded(preliminaryMapUploadedSpringEvent.getIdContract());
    }

    public void confirmPreliminaryMapUploaded(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        PreliminaryMapToUploadContract preliminaryMapToUploadContract = contractMapper.preliminaryMapToUploadStepFrom(contractEntity);
        preliminaryMapToUploadContract.setPreliminaryMapUpload(true);
        contractEntity.update(preliminaryMapToUploadContract);
        contractRepository.save(contractEntity);
    }

    public ContractDataDto confirmStep(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        Contract contract = contractMapper.from(contractEntity);
        Contract confirmContract = contract.confirmStep();
        contractEntity.updateFromContract(confirmContract);
        contractRepository.save(contractEntity);
        return confirmContract.dto();
    }

    public List<ContractDataDto> getContracts(int page) {
        return contractRepository.findAll(PageRequest.of(page, PAGE_SIZE))
                .stream()
                .map(contractEntity -> contractMapper.from(contractEntity).dto())
                .collect(Collectors.toList());
    }

}
