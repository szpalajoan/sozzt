package pl.jkap.sozzt.contract.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.jkap.sozzt.contract.model.Contract;
import pl.jkap.sozzt.contract.model.FileContract;
import pl.jkap.sozzt.contract.repository.ContractRepository;
import pl.jkap.sozzt.contract.repository.FileContractRepository;
import pl.jkap.sozzt.contract.service.stepContract.Step;
import pl.jkap.sozzt.contract.service.stepContract.StepChecker;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static pl.jkap.sozzt.contract.model.ContractStep.DATA_INPUT;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final FileContractRepository fileContractRepository;

    private static final int PAGE_SIZE = 5;

    public List<Contract> getContracts(int page) {
        return contractRepository.findAllContract(PageRequest.of(page, PAGE_SIZE));
    }

    public Contract getContract(long id) {
        return contractRepository.findById(id)
                .orElseThrow();
    }

    public Contract addContract(Contract contract) {
        contract.setContractStep(DATA_INPUT);
        return contractRepository.save(contract);
    }

    @Transactional
    public Contract editContract(Contract contract) {
        Contract contractEdited = contractRepository.findById(contract.getId()).orElseThrow();
        contractEdited.setInvoiceNumber(contract.getInvoiceNumber());
        contractEdited.setExecutive(contract.getExecutive());
        contractEdited.setLocation(contract.getLocation());
        contractEdited.setContractStep(contract.getContractStep());
        return contractEdited;
    }

    public void deleteContract(long id) {
        contractRepository.deleteById(id);
    }

    public List<Contract> getContractsWithFiles(int page) {
        List<Contract> allContracts = contractRepository.findAllContract(PageRequest.of(page, PAGE_SIZE));
        List<Long> ids = allContracts.stream()
                .map(Contract::getId)
                .collect(Collectors.toList());

        List<FileContract> filesContractData = fileContractRepository.findAllByIdContractIn(ids);
        allContracts.forEach(contract -> contract.setFilesContractData(extractFilesContractData(filesContractData, contract.getId())));
        return allContracts;
    }

    private List<FileContract> extractFilesContractData(List<FileContract> filesContractData, Long idContract) {
        return filesContractData.stream()
                .filter(fileContractData -> Objects.equals(fileContractData.getIdContract(), idContract))
                .collect(Collectors.toList());
    }

    public Contract validateStepContract(long idContract) {
        Contract contractEdited = contractRepository.findById(idContract).orElseThrow();

        StepChecker stepChecker = new StepChecker(idContract, contractEdited.getContractStep(), fileContractRepository);
        Step step = stepChecker.returnActualStep();

        Step stepAfterValidate = step.validateStep();
        contractEdited.setContractStep(stepAfterValidate.getContractStep());
        return contractEdited;
    }

}
