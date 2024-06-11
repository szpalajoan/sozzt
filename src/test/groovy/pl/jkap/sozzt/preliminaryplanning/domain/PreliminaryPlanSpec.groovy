package pl.jkap.sozzt.preliminaryplanning.domain

import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedPreliminaryMapAdditionException
import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.preliminaryplanning.exception.PreliminaryPlanNotFoundException
import pl.jkap.sozzt.sample.SozztSpecification

class PreliminaryPlanSpec extends SozztSpecification {


    def setup() {
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
    }

    def "should add preliminary plan"() {
        given: "There is a $KRYNICA_CONTRACT added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$KRYNICA_CONTRACT is completely introduced by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.finalizeIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Preliminary plan is added"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_CONTRACT.contractId) == KRYNICA_PRELIMINARY_PLAN
    }

    def "Should upload a preliminary map to preliminary plan"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        when: "$DAREK_PRELIMINARY_PLANER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        then: "$KRYNICA_PRELIMINARY_PLAN has a preliminary map uploaded"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [isPreliminaryMapUploaded : true])
    }

    def "Should not allow to upload a preliminary map to preliminary plan for not privileged user"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN"
           addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        when: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        then: "$MONIKA_CONTRACT_INTRODUCER is not allowed to modify $KRYNICA_PRELIMINARY_PLAN"
           thrown(RuntimeException)
    }

    def "should remove a preliminary map from preliminary plan"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan with a preliminary map uploaded"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        and: "$DAREK_PRELIMINARY_PLANER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            FileDto preliminaryMap = uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        when: "$DAREK_PRELIMINARY_PLANER removes $KRYNICA_PRELIMINARY_MAP from $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            deleteFile(preliminaryMap.fileId)
        then: "$KRYNICA_PRELIMINARY_PLAN preliminary plan has no preliminary map uploaded"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [isPreliminaryMapUploaded : false])
    }

    def "Should not upload a preliminary map to preliminary plan when preliminary plan does not exist"() {
        when: "$MONIKA_CONTRACT_INTRODUCER  try to upload $KRYNICA_PRELIMINARY_MAP to not existing $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        then: "$KRYNICA_PRELIMINARY_MAP is not uploaded"
            thrown(UnauthorizedPreliminaryMapAdditionException)
    }

    def "Should not upload preliminary map to preliminary plan when it is already uploaded"() { //TODO zastanów sie czy jest ten case i nad implementacja
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        when: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        then: "$KRYNICA_PRELIMINARY_PLAN preliminary plan has a preliminary map uploaded"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [isPreliminaryMapUploaded : true])
    }

    // TODO: czy tylko Darek moze dodawac mapy czy ktoś jeszcze
    // TODO: przypadek gdy nie ma kontraktu
}
