package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PublicPlotOwnerConsentDto
import pl.jkap.sozzt.sample.ContractFixture
import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.BEGIN_CONSENTS_COLLECTION
import static pl.jkap.sozzt.sample.ExpectedStageSample.COMPLETED_TERRAIN_VISION

class CompleteConsentsStepSpec extends SozztSpecification {

    def "should complete consents step and begin preparation of documentation step when route preparation is completed"() {
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
        and:"$KASIA_CONSENT_CORDINATOR fill $KRYNICA_ZUD_CONSENT"
            consentsFacade.updateZudConsent(KRYNICA_CONTRACT.contractId,
                    toUpdateZudConsent(KRYNICA_ZUD_CONSENT))
        and: "$IWONA_CONSENT_COLLECTOR adds agreement for $KRYNICA_ZUD_CONSENT"
            addZudConsentAgreement(KRYNICA_ZUD_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId)
        when: "$KASIA_CONSENT_CORDINATOR completes consents collection"
            consentsFacade.completeConsentsCollection(KRYNICA_CONTRACT.contractId)
        then: "consents step is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT
    }

    def "should complete consents step and begin preparation of documentation step when project purposes map preparation is not necessary"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION, new ContractFixture().withMapRequired(false))
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
        and:"$KASIA_CONSENT_CORDINATOR fill $KRYNICA_ZUD_CONSENT"
            consentsFacade.updateZudConsent(KRYNICA_CONTRACT.contractId,
                    toUpdateZudConsent(KRYNICA_ZUD_CONSENT))
        and: "$IWONA_CONSENT_COLLECTOR adds agreement for $KRYNICA_ZUD_CONSENT"
            addZudConsentAgreement(KRYNICA_ZUD_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId)
        when: "$KASIA_CONSENT_CORDINATOR completes consents collection"
            consentsFacade.completeConsentsCollection(KRYNICA_CONTRACT.contractId)
        then: "consents step is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_PROJECT_PURPOSES_MAP_PREPARATION
    }

}
