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
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        when: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            addPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        then: "$KRYNICA_PRELIMINARY_PLAN preliminary plan has a preliminary map uploaded"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [isPreliminaryMapUploaded : true])
    }
}
