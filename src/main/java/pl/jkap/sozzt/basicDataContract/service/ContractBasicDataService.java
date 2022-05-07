package pl.jkap.sozzt.basicDataContract.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jkap.sozzt.basicDataContract.model.ContractBasicData;
import pl.jkap.sozzt.basicDataContract.model.StatusContractData;
import pl.jkap.sozzt.basicDataContract.repository.ContractBasicDataRepository;
import pl.jkap.sozzt.basicDataContract.repository.StatusContractRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractBasicDataService {

    private final ContractBasicDataRepository contractBasicDataRepository;
    private final StatusContractRepository statusContractRepository;

    public List<ContractBasicData> getContractsBasicDataRepository(){
        return contractBasicDataRepository.findAll();
    }

    public ContractBasicData getContractBasicData(long id) {
        return contractBasicDataRepository.findById(id)
                .orElseThrow();
    }

    public ContractBasicData addContractBasicData(ContractBasicData contractBasicData) {
        StatusContractData statusContractData = statusContractRepository.findById(1L).orElseThrow();
        contractBasicData.setId_status_contract(statusContractData);
        return contractBasicDataRepository.save(contractBasicData);
    }

    @Transactional
    public ContractBasicData editContractBasicData(ContractBasicData contractBasicData) {
        ContractBasicData orderEdited = contractBasicDataRepository.findById(contractBasicData.getId_contract_basic_data()).orElseThrow();
        orderEdited.setInvoice_number(contractBasicData.getInvoice_number());
        orderEdited.setExecutive(contractBasicData.getExecutive());
        orderEdited.setLocation(contractBasicData.getLocation());
        return orderEdited;
    }

    public void deleteContractBasicData(long id) {
        contractBasicDataRepository.deleteById(id);
    }
}
