package pl.jkap.sozzt.contract.domain;

import lombok.extern.java.Log;
import org.springframework.transaction.annotation.Transactional;
import pl.jkap.sozzt.contract.dto.ContractDTO;

import static java.util.Objects.requireNonNull;

public class ContractFacade {

    private ContractRepository contractRepository;
    private ContractCreator contractCreator;

    public ContractFacade(ContractRepository contractRepository, ContractCreator contractCreator){
        this.contractCreator = contractCreator;
        this.contractRepository = contractRepository;
    }

    public ContractDTO addContract(ContractDTO contractDTO) {
        requireNonNull(contractDTO);
        Contract contract = contractCreator.from(contractDTO);
        contract = contractRepository.save(contract);
        return contract.dto();
    }

    public Contract getContract(long id) {
        return contractRepository.findById(id);
    }

    public Contract confirmStep(long id){
        Contract contract = contractRepository.findById(id);
        contract.confirmStep();
        return contractRepository.save(contract);
    }

}
