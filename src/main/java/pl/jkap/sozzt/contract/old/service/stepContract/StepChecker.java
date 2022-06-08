package pl.jkap.sozzt.contract.old.service.stepContract;

import pl.jkap.sozzt.contract.old.model.ContractStep;
import pl.jkap.sozzt.contract.old.repository.FileContractRepository;

import static pl.jkap.sozzt.contract.old.model.ContractStep.DATA_INPUT;

public class StepChecker {
    private final long idContract;
    private final FileContractRepository fileContractRepository;
    private final ContractStep contractStep;

    public StepChecker(long idContract,  ContractStep contractStep, FileContractRepository fileContractRepository) {
        this.idContract = idContract;
        this.fileContractRepository = fileContractRepository;
        this.contractStep = contractStep;
    }

    public Step returnActualStep() {
        if (contractStep.equals(DATA_INPUT)) {
            return new DataInputStep(idContract, fileContractRepository);
        } else {
            return new WaitingToPreliminaryStep();
        }
    }
}
