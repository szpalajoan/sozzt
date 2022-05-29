package pl.jkap.sozzt.contract.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.jkap.sozzt.contract.model.Contract;
import pl.jkap.sozzt.contract.model.FileContract;
import pl.jkap.sozzt.contract.model.StatusContract;
import pl.jkap.sozzt.contract.repository.ContractRepository;
import pl.jkap.sozzt.contract.repository.FileContractRepository;
import pl.jkap.sozzt.contract.repository.StatusContractRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final StatusContractRepository statusContractRepository;
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
        StatusContract statusContract = statusContractRepository.findById(1L).orElseThrow();
        contract.setIdStatusContract(statusContract);
        return contractRepository.save(contract);
    }

    @Transactional
    public Contract editContract(Contract contract) {
        Contract contractEdited = contractRepository.findById(contract.getId()).orElseThrow();
        contractEdited.setInvoiceNumber(contract.getInvoiceNumber());
        contractEdited.setExecutive(contract.getExecutive());
        contractEdited.setLocation(contract.getLocation());
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
}
