package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.dto.ContractDtoRepository;
import pl.jkap.sozzt.contract.dto.DataInputContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.file.event.PreliminaryMapUploadedSpringEvent;
import pl.jkap.sozzt.file.event.ScanFromTauronUploadedSpringEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class ContractFacade {

    private static final int PAGE_SIZE = 5;
    private final ContractRepository contractRepository;
    private final ContractCreator contractCreator;
    private ContractMapperInterface contractMapper;

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
        Contract contract = from(contractEntity);
        Contract confirmContract = contract.confirmStep();
        contractEntity.updateFromContract(confirmContract);
        contractRepository.save(contractEntity);
        return confirmContract.dto();
    }

    public List<ContractDataDto> getContracts(int page) {
        return contractRepository.findAll(PageRequest.of(page, PAGE_SIZE))
                .stream()
                .map(contractEntity -> from(contractEntity).dto())
                .collect(Collectors.toList());
    }

    private Contract from(ContractEntity contractEntity) {
        requireNonNull(contractEntity);

        switch (contractEntity.getContractStepEnum()) {
            case DATA_INPUT: {
                return contractMapper.dataInputStepFrom(contractEntity);
            }
            case PRELIMINARY_MAP_TO_UPLOAD: {
                return contractMapper.preliminaryMapToUploadStepFrom(contractEntity);
            }
            case PRELIMINARY_MAP_TO_VERIFY: {
                return contractMapper.preliminaryMapToVerifyStepFrom(contractEntity);
            }
            case LIST_OF_CONSENTS_TO_ADD: {
                return contractMapper.listOfConsentsToAddContractStepFrom(contractEntity);
            }
            case CONSENTS_TO_ACCEPT: {
                return contractMapper.consentsToAcceptContractStepFrom(contractEntity);
            }
            default: {
                throw new NotYetImplementedException();
            }
        }
    }

}
