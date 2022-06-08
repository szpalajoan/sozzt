package pl.jkap.sozzt.contract.service.stepContract

import pl.jkap.sozzt.contract.old.repository.FileContractRepository
import pl.jkap.sozzt.contract.old.service.stepContract.DataInputStep
import pl.jkap.sozzt.contract.old.service.stepContract.Step
import pl.jkap.sozzt.contract.old.service.stepContract.StepChecker
import pl.jkap.sozzt.contract.old.service.stepContract.WaitingToPreliminaryStep
import spock.lang.Specification

import static pl.jkap.sozzt.contract.old.model.ContractStep.DATA_INPUT


class StepCheckerTest extends Specification {

    private FileContractRepository fileContractRepository

    void setup() {
        fileContractRepository = Stub(FileContractRepository.class)
    }

    def "When administrator validate contract then proper handling of this step is returned"() {
        given: "There is contract with some step"
        StepChecker stepChecker = new StepChecker(idContract, contractStep, fileContractRepository);

        when: "the step is checked"
        Step step = stepChecker.returnActualStep();

        then: "proper handling of this step is returned"
        step.class == handlingClassOfThisStep

        where:
        contractStep           | handlingClassOfThisStep
        DATA_INPUT             | DataInputStep.class
        WAITING_TO_PRELIMINARY | WaitingToPreliminaryStep.class

        idContract = 1

    }
}
