package pl.jkap.sozzt.landextracts.domain

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class LandExtractsSpec extends SozztSpecification {

    def "should add land extracts when terrain vision is completed"() {
        when: "terrain vision is completed"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION)
        then: "Land extracts preparation is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
            landExtractsFacade.getLandExtracts(KRYNICA_CONTRACT.contractId) == KRYNICA_LAND_EXTRACTS
    }

    def "should mark send request for plot extracts as done"() {
        given: "there is $KRYNICA_LAND_EXTRACTS stage"
            addKrynicaContractOnStage(BEGIN_LAND_EXTRACTS)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        when: "$KASIA_CONSENT_CORDINATOR marks send request for plot extracts as done"
            landExtractsFacade.requestForLandExtractsSent(KRYNICA_CONTRACT.contractId)
        then: "Send request for plot extracts is marked as done"
            landExtractsFacade.getLandExtracts(KRYNICA_CONTRACT.contractId) == with(KRYNICA_LAND_EXTRACTS, [requestForPlotExtractsSent: true])
    }

    def "should complete land extracts collection"() {
        given: "there is $KRYNICA_LAND_EXTRACTS stage"
            addKrynicaContractOnStage(BEGIN_LAND_EXTRACTS)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR marked send request for plot extracts as done"
            landExtractsFacade.requestForLandExtractsSent(KRYNICA_CONTRACT.contractId)
        when: "$KASIA_CONSENT_CORDINATOR completes land extracts collection"
            landExtractsFacade.completeLandExtracts(KRYNICA_CONTRACT.contractId)
        then: "Land extracts collection is marked as completed"
            landExtractsFacade.getLandExtracts(KRYNICA_CONTRACT.contractId) == COMPLETED_KRYNICA_LAND_EXTRACTS
    }
} 