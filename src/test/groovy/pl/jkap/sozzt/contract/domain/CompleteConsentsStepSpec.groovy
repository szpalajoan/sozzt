package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PublicPlotOwnerConsentDto
import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.BEGIN_CONSENTS_COLLECTION
import static pl.jkap.sozzt.sample.ExpectedStageSample.COMPLETED_TERRAIN_VISION_WITHOUT_MAP_REQUIRED

class CompleteConsentsStepSpec extends SozztSpecification {

    def "should complete consents step and begin preparation of documentation step when route preparation is completed"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KRYNICA_ROUTE_PREPARATION is completed"
            loginUser(WALDEK_SURVEYOR)
            completeRoutePreparation(COMPLETED_KRYNICA_ROUTE_PREPARATION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PUBLIC_OWNER_CONSENT"
            PublicPlotOwnerConsentDto publicOwnerConsentDto = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            PrivatePlotOwnerConsentDto privatePlotOwnerConsentDto = consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        and: "$KASIA_CONSENT_CORDINATOR added agreement for $KRYNICA_PUBLIC_OWNER_CONSENT"
            addPublicOwnerConsentAgreement(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, publicOwnerConsentDto.publicPlotOwnerConsentId)
        and: "$KASIA_CONSENT_CORDINATOR added agreement for $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            addPrivatePlotOwnerConsentAgreement(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, privatePlotOwnerConsentDto.privatePlotOwnerConsentId)
        when: "$KASIA_CONSENT_CORDINATOR completes consents collection"
            consentsFacade.completeConsentsCollection(KRYNICA_CONTRACT.contractId)
        then: "consents step is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT
    }

    def "should complete consents step and begin preparation of documentation step when route preparation is not necessary"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION_WITHOUT_MAP_REQUIRED)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PUBLIC_OWNER_CONSENT"
            PublicPlotOwnerConsentDto publicOwnerConsentDto = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            PrivatePlotOwnerConsentDto privatePlotOwnerConsentDto = consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        and: "$KASIA_CONSENT_CORDINATOR added agreement for $KRYNICA_PUBLIC_OWNER_CONSENT"
            addPublicOwnerConsentAgreement(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, publicOwnerConsentDto.publicPlotOwnerConsentId)
        and: "$KASIA_CONSENT_CORDINATOR added agreement for $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            addPrivatePlotOwnerConsentAgreement(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, privatePlotOwnerConsentDto.privatePlotOwnerConsentId)
        when: "$KASIA_CONSENT_CORDINATOR completes consents collection"
            consentsFacade.completeConsentsCollection(KRYNICA_CONTRACT.contractId)
        then: "consents step is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_ROUTE_PREPARATION_STEP
    }

    def "should complete consents step and not begin preparation of documentation step when route preparation is not completed"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PUBLIC_OWNER_CONSENT"
            PublicPlotOwnerConsentDto publicOwnerConsentDto = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            PrivatePlotOwnerConsentDto privatePlotOwnerConsentDto = consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        and: "$KASIA_CONSENT_CORDINATOR added agreement for $KRYNICA_PUBLIC_OWNER_CONSENT"
            addPublicOwnerConsentAgreement(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, publicOwnerConsentDto.publicPlotOwnerConsentId)
        and: "$KASIA_CONSENT_CORDINATOR added agreement for $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            addPrivatePlotOwnerConsentAgreement(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, privatePlotOwnerConsentDto.privatePlotOwnerConsentId)
        when: "$KASIA_CONSENT_CORDINATOR completes consents collection"
            consentsFacade.completeConsentsCollection(KRYNICA_CONTRACT.contractId)
        then: "consents step is completed without beginning preparation of documentation step"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT_WITH_NOT_COMPLETED_ROUTE_PREPARATION_STEP
    }

}
