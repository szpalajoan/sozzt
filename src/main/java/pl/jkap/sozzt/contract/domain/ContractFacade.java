package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.consents.event.ConsentsCollectionCompletedEvent;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;
import pl.jkap.sozzt.contract.dto.EditContractDto;
import pl.jkap.sozzt.contract.exception.ContractFinalizeException;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade;
import pl.jkap.sozzt.contractsecurity.dto.AddSecurityContractDto;
import pl.jkap.sozzt.documentation.event.DocumentationCompletedEvent;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.filestorage.event.ContractScanDeletedEvent;
import pl.jkap.sozzt.preliminaryplanning.event.PreliminaryPlanCompletedEvent;
import pl.jkap.sozzt.remark.domain.RemarkFacade;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade;
import pl.jkap.sozzt.projectpurposesmappreparation.event.ProjectPurposesMapPreparationCompletedEvent;
import pl.jkap.sozzt.routedrawing.domain.RoutePreparationFacade;
import pl.jkap.sozzt.routedrawing.event.RoutePreparationCompletedEvent;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;
import pl.jkap.sozzt.terrainvision.event.TerrainVisionCompletedEvent;

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
    private final TerrainVisionFacade terrainVisionFacade;
    private final ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade;
    private final RoutePreparationFacade routePreparationFacade;
    private final RemarkFacade remarkFacade;

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


    public ContractDto editContract(UUID idContract, EditContractDto editContractDto) {
        requireNonNull(editContractDto);
        contractSecurityFacade.checkCanEditContract();

        Contract contract = findContract(idContract);
        contract.edit(editContractDto);
        contract = contractRepository.save(contract);

        ContractDto editedContract = contract.dto();
        log.info("Contract edited: {}", editedContract);
        return editedContract;
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


    public void finalizeContractIntroduction(UUID contractId) {
        contractSecurityFacade.checkCanFinalizeContractIntroduction();
        Contract contract = findContract(contractId);
        if(!contract.isContractCompleted()) {
            throw new ContractFinalizeException("Contract is not completed: " + contractId);
        }
        contract.createSteps(contractStepCreator);
        contractRepository.save(contract);
        log.info("Contract finalized: {}", contract);
    }

    private void completePreliminaryPlan(UUID contractId) {
        Contract contract = findContract(contractId);
        contract.completePreliminaryPlan(terrainVisionFacade);
        contractRepository.save(contract);
        log.info("Preliminary plan finalized: {}", contract);
    }

    private void completeTerrainVision(UUID contractId, boolean projectPurposesMapPreparationNeed) {
        Contract contract = findContract(contractId);
        contract.completeTerrainVision(projectPurposesMapPreparationFacade, routePreparationFacade, projectPurposesMapPreparationNeed);
        contractRepository.save(contract);
        log.info("Terrain vision finalized: {}", contract);
    }

    private void completeProjectPurposesMapPreparation(UUID contractId) {
        Contract contract = findContract(contractId);
        contract.completeProjectPurposesMapPreparation(routePreparationFacade);
        contractRepository.save(contract);
        log.info("Project purposes map preparation finalized: {}", contract);
    }

    private void completeRoutePreparation(UUID contractId) {
        Contract contract = findContract(contractId);
        contract.completeRoutePreparation();
        contractRepository.save(contract);
        log.info("Route preparation finalized: {}", contract);
    }

    private void completeConsentsCollection(UUID contractId) {
        Contract contract = findContract(contractId);
        contract.completeConsentsCollection();
        contractRepository.save(contract);
        log.info("Consents collection finalized: {}", contract);
    }

    private void completeDocumentation(UUID contractId) {
        Contract contract = findContract(contractId);
        boolean hasActiveRemarks = remarkFacade.hasActiveRemarksForContract(contractId);
        if (hasActiveRemarks) {
            throw new ContractFinalizeException("Contract has active remarks, cannot finalize documentation: " + contractId);
        }
        contract.completePreparationDocumentation();
        contractRepository.save(contract);
        log.info("Documentation completed for contract: {}", contractId);
    }

    private void scanUploaded(UUID contractId) {
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
    @SuppressWarnings("unused")
    public void onContractScanAddedEvent(ContractScanAddedEvent event) {
        scanUploaded(event.getContractId());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onContractScanDeletedEvent(ContractScanDeletedEvent event) {
        scanDeleted(event.getContractId());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onPreliminaryPlanCompletedEvent(PreliminaryPlanCompletedEvent event) {
        completePreliminaryPlan(event.getContractId());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onTerrainVisionCompletedEvent(TerrainVisionCompletedEvent event) {
        completeTerrainVision(event.getContractId(), event.isProjectPurposesMapPreparationNeed());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onProjectPurposesMapPreparationCompletedEvent(ProjectPurposesMapPreparationCompletedEvent event) {
        completeProjectPurposesMapPreparation(event.getContractId());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onRoutePreparationCompletedEvent(RoutePreparationCompletedEvent event) {
        completeRoutePreparation(event.getContractId());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onConsentsCollectionCompletedEvent(ConsentsCollectionCompletedEvent event) {
        completeConsentsCollection(event.getContractId());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onDocumentationCompletedEvent(DocumentationCompletedEvent event) {
        completeDocumentation(event.getContractId());
    }

}
