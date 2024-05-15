package pl.jkap.sozzt.preliminaryplanning.domain

import pl.jkap.sozzt.sample.SozztSpecification

class PreliminaryPlanSpec extends SozztSpecification {

    def "should add terrain vision"() {
        given: "There is a $KRYNICA_CONTRACT"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$KRYNICA_CONTRACT is completly introduced by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.finalizeIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Preliminary plan is added"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_CONTRACT.contractId) == KRYNICA_PRELIMINARY_PLAN
    }
}
