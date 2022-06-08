package pl.jkap.sozzt.contract.domain

import spock.lang.Specification


class ContractSpec extends Specification implements ContractSample {

    ContractFacade contractFacade = new ContractConfiguration().dataInputContractFacade();

    def "should add contract"(){

        when:"User add new contract"
        def contract = contractFacade.addContract(MEDIUM_VOLTAG_NETWORK_IN_TARNOW)

        then:"new contract is added"
        contract == MEDIUM_VOLTAG_NETWORK_IN_TARNOW

        and: "contract step is 'data input'"
        contractFacade.getContract(MEDIUM_VOLTAG_NETWORK_IN_TARNOW.id).getContractStep().class == DataInputStep.class
    }

    def "should confirm 'data input' step"(){
        given: "there is a contract with 'data input' step"
        contractFacade.addContract(MEDIUM_VOLTAG_NETWORK_IN_TARNOW)

        when:"confirm contract"
        contractFacade.getContract(MEDIUM_VOLTAG_NETWORK_IN_TARNOW.id).confirmStep()

        then: "costract has WaitingToPreliminaryMapStep"
        contractFacade.getContract(MEDIUM_VOLTAG_NETWORK_IN_TARNOW.id).getContractStep().class == WaitingToPreliminaryMapStep.class

    }
}
