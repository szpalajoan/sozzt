package pl.jkap.sozzt.preliminaryplanning.domain

import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedPreliminaryMapAdditionException
import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.filestorage.exception.FileAlreadyExistsException
import pl.jkap.sozzt.preliminaryplanning.exception.PreliminaryPlanNotFoundException
import pl.jkap.sozzt.sample.SozztSpecification

class PreliminaryPlanSpec extends SozztSpecification {


    def setup() {
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
    }

    def "should add preliminary plan"() {
        when: "$KRYNICA_CONTRACT is completely introduced by $MONIKA_CONTRACT_INTRODUCER"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        then: "Preliminary plan is added"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_CONTRACT.contractId) == KRYNICA_PRELIMINARY_PLAN
    }

    def "Should upload a preliminary map to preliminary plan"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        when: "$DAREK_PRELIMINARY_PLANER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_PLAN, KRYNICA_PRELIMINARY_MAP)
        then: "$KRYNICA_PRELIMINARY_PLAN has a preliminary map uploaded"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [isPreliminaryMapUploaded : true])
    }

    def "Should not allow to upload a preliminary map to preliminary plan for not privileged user"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN"
           addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        when: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_PLAN, KRYNICA_PRELIMINARY_MAP)
        then: "$MONIKA_CONTRACT_INTRODUCER is not allowed to modify $KRYNICA_PRELIMINARY_PLAN"
           thrown(RuntimeException)
    }

    def "should remove a preliminary map from preliminary plan"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan with a preliminary map uploaded"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        and: "$DAREK_PRELIMINARY_PLANER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            FileDto preliminaryMap = uploadPreliminaryMap(KRYNICA_PRELIMINARY_PLAN, KRYNICA_PRELIMINARY_MAP)
        when: "$DAREK_PRELIMINARY_PLANER removes $KRYNICA_PRELIMINARY_MAP from $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            deleteFile(preliminaryMap.fileId)
        then: "$KRYNICA_PRELIMINARY_PLAN preliminary plan has no preliminary map uploaded"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [isPreliminaryMapUploaded : false])
    }

    def "Should not upload a preliminary map to preliminary plan when preliminary plan does not exist"() {
        when: "$MONIKA_CONTRACT_INTRODUCER  try to upload $KRYNICA_PRELIMINARY_MAP to not existing $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_PLAN, KRYNICA_PRELIMINARY_MAP)
        then: "$KRYNICA_PRELIMINARY_MAP is not uploaded"
            thrown(UnauthorizedPreliminaryMapAdditionException)
    }

    def "Should not upload preliminary map to preliminary plan when it is already uploaded"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        and: "$KRYNICA_PRELIMINARY_MAP is uploaded to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_PLAN, KRYNICA_PRELIMINARY_MAP)
        when: "$DAREK_PRELIMINARY_PLANER try to uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN preliminary plan again"
            uploadPreliminaryMap(KRYNICA_PRELIMINARY_PLAN, KRYNICA_PRELIMINARY_MAP)
        then: "$KRYNICA_PRELIMINARY_MAP is not uploaded again"
            thrown(FileAlreadyExistsException)
    }

    def "should add google map url to preliminary plan"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        when: "$DAREK_PRELIMINARY_PLANER adds $KRYNICA_GOOGLE_MAP_URL to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            preliminaryPlanFacade.addGoogleMapUrl(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId, KRYNICA_GOOGLE_MAP_URL)
        then: "$KRYNICA_PRELIMINARY_PLAN preliminary plan has google map url added"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [googleMapUrl : KRYNICA_GOOGLE_MAP_URL])
    }

    def "Should not add google map url to preliminary plan when preliminary plan does not exist"() {
        when: "$DAREK_PRELIMINARY_PLANER  try to add $KRYNICA_GOOGLE_MAP_URL to not existing $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            preliminaryPlanFacade.addGoogleMapUrl(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId, KRYNICA_GOOGLE_MAP_URL)
        then: "$KRYNICA_GOOGLE_MAP_URL is not added"
            thrown(PreliminaryPlanNotFoundException)
    }

    def "Should update google map url to preliminary plan when google map url is already added"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        and: "$KRYNICA_GOOGLE_MAP_URL is added to $KRYNICA_PRELIMINARY_PLAN preliminary plan"
            preliminaryPlanFacade.addGoogleMapUrl(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId, KRYNICA_GOOGLE_MAP_URL)
        when: "$DAREK_PRELIMINARY_PLANER add new $UPDATED_KRYNICA_GOOGLE_MAP_URL to $KRYNICA_PRELIMINARY_PLAN preliminary plan again"
            preliminaryPlanFacade.addGoogleMapUrl(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId, UPDATED_KRYNICA_GOOGLE_MAP_URL)
        then: "google map url for $KRYNICA_PRELIMINARY_PLAN is updated to $UPDATED_KRYNICA_GOOGLE_MAP_URL"
            preliminaryPlanFacade.getPreliminaryPlan(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId) == with(KRYNICA_PRELIMINARY_PLAN, [googleMapUrl : UPDATED_KRYNICA_GOOGLE_MAP_URL])
    }
}
