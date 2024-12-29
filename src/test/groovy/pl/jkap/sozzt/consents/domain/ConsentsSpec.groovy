package pl.jkap.sozzt.consents.domain

import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PublicOwnerConsentDto
import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.COMPLETED_TERRAIN_VISION
import static pl.jkap.sozzt.sample.ExpectedStageSample.BEGIN_CONSENTS_COLLECTION

class ConsentsSpec extends SozztSpecification {

    def "should add consents preparation when terrain vision is completed"() {
        when: "terrain vision is completed"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION)
        then: "Consents preparation is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == KRYNICA_CONSENTS
    }

    def "should mark send request for plot extracts as done"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        when: "$KASIA_CONSENT_CORDINATOR marks send request for plot extracts as done"
            consentsFacade.requestForLandExtractsSent(KRYNICA_CONTRACT.contractId)
        then: "Send request for plot extracts is marked as done"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [requestForPlotExtractsSent: true])
    }

    def "should add new private plot owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        when: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        then: "New consent $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT is added"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [privatePlotOwnerConsents: [KRYNICA_PRIVATE_PLOT_OWNER_CONSENT]])
    }

    def "should add new public owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        when: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PUBLIC_OWNER_CONSENT"
            consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        then: "New consent $KRYNICA_PUBLIC_OWNER_CONSENT is added"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [publicOwnerConsents: [KRYNICA_PUBLIC_OWNER_CONSENT]])
    }

    def "should add agreement for private plot owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            PrivatePlotOwnerConsentDto privatePlotOwnerConsentDto = consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        and: "$IWONA_CONSENT_COLLECTOR is logged in $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
            loginUser(IWONA_CONSENT_COLLECTOR)
        when: "$IWONA_CONSENT_COLLECTOR adds $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT"
            FileDto privatePlotOwnerConsentAgreement = addPrivatePlotOwnerConsentAgreement(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT, KRYNICA_CONTRACT.contractId, privatePlotOwnerConsentDto.privatePlotOwnerConsentId)
        then: "$KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT is added"
            privatePlotOwnerConsentAgreement == KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_METADATA
        and: "$KRYNICA_PRIVATE_PLOT_OWNER_CONSENT is accepted"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [privatePlotOwnerConsents: [with(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT, [consentStatus: ConsentStatus.CONSENT_GIVEN, consentGivenDate: TOMORROW])]])
    }

    def "should add agreement for public owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PUBLIC_OWNER_CONSENT"
            PublicOwnerConsentDto publicOwnerConsentDto = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        and: "$IWONA_CONSENT_COLLECTOR is logged in $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
            loginUser(IWONA_CONSENT_COLLECTOR)
        when: "$IWONA_CONSENT_COLLECTOR adds $KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT"
            FileDto publicOwnerConsentAgreement = addPublicOwnerConsentAgreement(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT, KRYNICA_CONTRACT.contractId, publicOwnerConsentDto.publicPlotOwnerConsentId)
        then: "$KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT is added"
            publicOwnerConsentAgreement == KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_METADATA
        and: "$KRYNICA_PUBLIC_OWNER_CONSENT is accepted"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [publicOwnerConsents: [with(KRYNICA_PUBLIC_OWNER_CONSENT, [consentStatus   : ConsentStatus.CONSENT_GIVEN,
                                                                                                                                                         consentGivenDate: TOMORROW])]])
    }

}
