package pl.jkap.sozzt.contract.service.stepContract;

import pl.jkap.sozzt.contract.model.ContractStep;
import pl.jkap.sozzt.contract.repository.FileContractRepository;

import static pl.jkap.sozzt.contract.model.ContractStep.DATA_INPUT;

public class StepChecker {
    private final long idContract;
    private final FileContractRepository fileContractRepository;

    public StepChecker(long idContract, FileContractRepository fileContractRepository) {
        this.idContract = idContract;
        this.fileContractRepository = fileContractRepository;
    }

    public Step returnActualStep(ContractStep contractStep) {
        if (contractStep.equals(DATA_INPUT)) {
            return new DataInputStep(idContract, fileContractRepository);
        } else {
            return new WaitingToPreliminaryStep();
        }
    }
}
