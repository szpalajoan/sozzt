package pl.jkap.sozzt.preliminaryplanning.domain

import pl.jkap.sozzt.sample.SozztSpecification

class FinalizePreliminaryPlanSpec extends SozztSpecification {


    def setup() {
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
        loginUser(MONIKA_CONTRACT_INTRODUCER)
    }

    def "Should finalize preliminary plan"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        and: "$KRYNICA_PRELIMINARY_MAP is uploaded to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        and: "$KRYNICA_GOOGLE_MAP_URL is added to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            preliminaryPlanFacade.addGoogleMapUrl(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId, KRYNICA_GOOGLE_MAP_URL)
        when: "$DAREK_PRELIMINARY_PLANER finalize $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            contractFacade.finalizePreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId)
        then: "$KRYNICA_PRELIMINARY_PLAN preliminary plan step is completed and contract is moved to terrain vision step"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_PRELIMINARY_PLAN_KRYNICA_CONTRACT
    }

}
