package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.consent.event.AllConsentAcceptedSpringEvent;
import pl.jkap.sozzt.consent.event.ConsentRejectSpringEvent;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.dto.ContractDtoRepository;
import pl.jkap.sozzt.contract.dto.DataInputContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.file.event.PreliminaryMapUploadedSpringEventContract;
import pl.jkap.sozzt.file.event.ScanFromTauronUploadedSpringEventContract;

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
    public void uploadedScanFromTauron(ScanFromTauronUploadedSpringEventContract scanFromTauronUploadedSpringEvent) {
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
    public void uploadedPreliminaryMap(PreliminaryMapUploadedSpringEventContract preliminaryMapUploadedSpringEvent) {
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
                .map(contractEntity -> contractMapper.from(contractEntity)
                        .dto())
                .collect(Collectors.toList());
    }

    @EventListener
    public void consentRejected(ConsentRejectSpringEvent consentRejectSpringEvent) {
        withdrawalContract(consentRejectSpringEvent.getIdContract());
    }

    private void withdrawalContract(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        Contract contract = contractMapper.from(contractEntity);
        PreliminaryMapToUploadContract withdrawContract = contract.withdrawalToNewPreliminaryMapUpload();
        contractEntity.update(withdrawContract);
        contractRepository.save(contractEntity);
    }

    @EventListener
    public void allConsentAccepted(AllConsentAcceptedSpringEvent allConsentAcceptedSpringEvent) {
        setAllConsentAcceptedInContract(allConsentAcceptedSpringEvent.getIdContract());
    }

    private void setAllConsentAcceptedInContract(UUID idContract) {
        ContractEntity contractEntity = contractRepository.findById(idContract)
                .orElseThrow(ContractNotFoundException::new);
        ConsentsToAcceptContract contract = contractMapper.consentsToAcceptContractStepFrom(contractEntity);
        contract.setAllConsentAccepted();
        contractEntity.update(contract);
        contractRepository.save(contractEntity);
    }

}
