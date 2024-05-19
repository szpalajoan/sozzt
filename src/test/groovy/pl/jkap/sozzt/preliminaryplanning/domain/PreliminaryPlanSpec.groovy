package pl.jkap.sozzt.preliminaryplanning.domain

import pl.jkap.sozzt.sample.SozztSpecification

class PreliminaryPlanSpec extends SozztSpecification {

    def "should add preliminary plan"() {
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        and: "There is a $KRYNICA_CONTRACT"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$KRYNICA_CONTRACT is completely introduced by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.finalizeIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Preliminary plan is added"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_CONTRACT.contractId) == KRYNICA_PRELIMINARY_PLAN
    }

    def "Should upload a preliminary map to preliminary plan"() {
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        and: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            addCompletelyIntroduceContract(contractFacade, KRYNICA_CONTRACT)
        when: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_CONTRACT_SCAN_FILE to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            fileStorageFacade.addContractScan(toAddContractScanFileDto(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT_SCAN_FILE, KRYNICA_CONTRACT))
        then: "$KRYNICA_PRELIMINARY_PLAN preliminary plan has a preliminary map uploaded"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_CONTRACT.contractId) == with(KRYNICA_PRELIMINARY_PLAN, [isPreliminaryMapUploaded : true])
    }
}
