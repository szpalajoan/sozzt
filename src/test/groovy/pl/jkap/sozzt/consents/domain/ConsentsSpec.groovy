package pl.jkap.sozzt.consents.domain

import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PublicPlotOwnerConsentDto
import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.sample.ContractFixture
import pl.jkap.sozzt.sample.SozztSpecification

import java.time.Instant

import static pl.jkap.sozzt.consents.domain.ConsentStatus.*
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

    def "should update private plot owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "there is $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT in $KRYNICA_CONSENTS"
            PrivatePlotOwnerConsentDto privatePlotOwnerConsent = consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        when: "$KASIA_CONSENT_CORDINATOR updates $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            consentsFacade.updatePrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId,
                    privatePlotOwnerConsent.privatePlotOwnerConsentId,
                    toUpdatePrivatePlotOwnerConsent(with(privatePlotOwnerConsent, [(updateField): updateValue])))
        then: "Private plot owner consent is updated"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [privatePlotOwnerConsents: [with(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT, [(updateField): updateValue])]])
        where:
            updateField     | updateValue
            "ownerName"     | "Piotr Kowalski"
            "comment"       | "Wire has to be replaced"
            "plotNumber"    | "23/123"
            "collectorName" | "BEATA"
            "mailingDate"   | Instant.now()
            "deliveryType"  | DeliveryType.POST
    }

    def "should update public owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "there is $KRYNICA_PUBLIC_OWNER_CONSENT in $KRYNICA_CONSENTS"
            PublicPlotOwnerConsentDto publicOwnerConsent = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        when: "$KASIA_CONSENT_CORDINATOR updates $KRYNICA_PUBLIC_OWNER_CONSENT"
            consentsFacade.updatePublicOwnerConsent(KRYNICA_CONTRACT.contractId,
                    publicOwnerConsent.publicPlotOwnerConsentId,
                    toUpdatePublicPlotOwnerConsent(with(publicOwnerConsent, [(updateField): updateValue])))
        then: "Comment is added"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [publicOwnerConsents: [with(KRYNICA_PUBLIC_OWNER_CONSENT, [(updateField): updateValue])]])
        where:
            updateField       | updateValue
            "publicOwnerName" | "Urząd miasta Krakowa"
            "comment"         | "Procedural delays"
            "plotNumber"      | "23/123"
            "collectorName"   | "BEATA"
            "mailingDate"     | Instant.now()
            "deliveryType"    | DeliveryType.POST
    }

    def "should mark private plot owner consent as sent by mail"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "there is $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT in $KRYNICA_CONSENTS"
            PrivatePlotOwnerConsentDto privatePlotOwnerConsent = consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        and: "it is $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
        when: "$KASIA_CONSENT_CORDINATOR marks $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT as sent by mail"
            consentsFacade.markPrivatePlotOwnerConsentAsSentByMail(KRYNICA_CONTRACT.contractId, privatePlotOwnerConsent.privatePlotOwnerConsentId)
        then: "Private plot owner consent is marked as sent by mail"
        consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [privatePlotOwnerConsents: [with(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT, [consentStatus: SENT,
                                                                                                                                                                mailingDate  : TOMORROW,
                                                                                                                                                                deliveryType : DeliveryType.POST])]])
    }

    def "should mark public plot owner consent as sent by mail"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "there is $KRYNICA_PUBLIC_OWNER_CONSENT in $KRYNICA_CONSENTS"
            PublicPlotOwnerConsentDto publicOwnerConsent = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        and: "it is $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
        when: "$KASIA_CONSENT_CORDINATOR marks $KRYNICA_PUBLIC_OWNER_CONSENT as sent by mail"
            consentsFacade.markPublicPlotOwnerConsentAsSentByMail(KRYNICA_CONTRACT.contractId, publicOwnerConsent.publicPlotOwnerConsentId)
        then: "Public plot owner consent is marked as sent by mail"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [publicOwnerConsents: [with(KRYNICA_PUBLIC_OWNER_CONSENT, [consentStatus: SENT,
                                                                                                                                                                mailingDate  : TOMORROW,
                                                                                                                                                                deliveryType : DeliveryType.POST])]])
    }

    def "should invalidate private owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "there is $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT in $KRYNICA_CONSENTS"
            PrivatePlotOwnerConsentDto privateOwnerConsent = consentsFacade.addPrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPrivatePlotOwnerConsent(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT))
        and: "it is $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
        when: "$KASIA_CONSENT_CORDINATOR invalidates $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            consentsFacade.invalidatePrivatePlotOwnerConsent(KRYNICA_CONTRACT.contractId, privateOwnerConsent.privatePlotOwnerConsentId, "Refused to give agreement")
        then: "Private plot owner consent is invalidated"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [privatePlotOwnerConsents: [with(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT, [consentStatus   : INVALIDATED,
                                                                                                                                                                    consentGivenDate: TOMORROW,
                                                                                                                                                                    statusComment   : "Refused to give agreement"])]])

    }

    def "should invalidate public owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "there is $KRYNICA_PUBLIC_OWNER_CONSENT in $KRYNICA_CONSENTS"
            PublicPlotOwnerConsentDto publicOwnerConsent = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        and: "it is $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
        when: "$KASIA_CONSENT_CORDINATOR invalidates $KRYNICA_PUBLIC_OWNER_CONSENT"
            consentsFacade.invalidatePublicPlotOwnerConsent(KRYNICA_CONTRACT.contractId, publicOwnerConsent.publicPlotOwnerConsentId, "consent not needed")
        then: "Public plot owner consent is invalidated"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [publicOwnerConsents: [with(KRYNICA_PUBLIC_OWNER_CONSENT, [consentStatus   : INVALIDATED,
                                                                                                                                                         consentGivenDate: TOMORROW,
                                                                                                                                                         statusComment   : "consent not needed"])]])
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
        when: "$IWONA_CONSENT_COLLECTOR adds agreement for $KRYNICA_PRIVATE_PLOT_OWNER_CONSENT"
            FileDto privatePlotOwnerConsentAgreement = addPrivatePlotOwnerConsentAgreement(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, privatePlotOwnerConsentDto.privatePlotOwnerConsentId)
        then: "$KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_SCAN is added"
            privatePlotOwnerConsentAgreement == KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_METADATA
        and: "$KRYNICA_PRIVATE_PLOT_OWNER_CONSENT is accepted"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [privatePlotOwnerConsents: [with(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT, [consentStatus: CONSENT_GIVEN, consentGivenDate: TOMORROW])]])
    }

    def "should add agreement for public owner consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR adds new $KRYNICA_PUBLIC_OWNER_CONSENT"
            PublicPlotOwnerConsentDto publicOwnerConsentDto = consentsFacade.addPublicOwnerConsent(KRYNICA_CONTRACT.contractId, toAddPublicPlotOwnerConsent(KRYNICA_PUBLIC_OWNER_CONSENT))
        and: "$IWONA_CONSENT_COLLECTOR is logged in $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
            loginUser(IWONA_CONSENT_COLLECTOR)
        when: "$IWONA_CONSENT_COLLECTOR adds agreement for $KRYNICA_PUBLIC_OWNER_CONSENT"
            FileDto publicOwnerConsentAgreement = addPublicOwnerConsentAgreement(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId, publicOwnerConsentDto.publicPlotOwnerConsentId)
        then: "$KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_SCAN is added"
            publicOwnerConsentAgreement == KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_METADATA
        and: "$KRYNICA_PUBLIC_OWNER_CONSENT is accepted"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [publicOwnerConsents: [with(KRYNICA_PUBLIC_OWNER_CONSENT, [consentStatus   : CONSENT_GIVEN,
                                                                                                                                                         consentGivenDate: TOMORROW])]])
    }

    def "should add new ZUD consent when is required"() {
        given: "there is $KRYNICA_CONSENTS stage with required ZUD consent"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        expect: "New consent $KRYNICA_ZUD_CONSENT is added"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [zudConsent: REQUIRED_EMPTY_ZUD_CONSENT])
    }

    def "should fill ZUD consent"() {
        given: "there is $KRYNICA_CONSENTS stage with required ZUD consent"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        when: "$KASIA_CONSENT_CORDINATOR updates $KRYNICA_ZUD_CONSENT"
            consentsFacade.updateZudConsent(KRYNICA_CONTRACT.contractId,
                    toUpdateZudConsent(KRYNICA_ZUD_CONSENT))
        then: "ZUD consent is updated"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [zudConsent: KRYNICA_ZUD_CONSENT])
    }


    def "should update ZUD consent"() {
        given: "there is $KRYNICA_CONSENTS stage with required ZUD consent"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        when: "$KASIA_CONSENT_CORDINATOR updates $KRYNICA_ZUD_CONSENT"
            consentsFacade.updateZudConsent(KRYNICA_CONTRACT.contractId,
                    toUpdateZudConsent(with(KRYNICA_ZUD_CONSENT, [(updateField): updateValue])))
        then: "ZUD consent is updated"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [zudConsent: with(KRYNICA_ZUD_CONSENT, [(updateField): updateValue])])
        where:
            updateField       | updateValue
            "institutionName" | "ZUD Kraków"
            "comment"        | "Wymaga dodatkowych uzgodnień"
            "plotNumber"     | "23/123"
            "collectorName"  | "BEATA"
            "mailingDate"    | Instant.now()
            "deliveryType"   | DeliveryType.POST
    }

    def "should mark ZUD consent as sent by mail"() {
        given: "there is $KRYNICA_CONSENTS stage with required ZUD consent"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        "$KASIA_CONSENT_CORDINATOR fill $KRYNICA_ZUD_CONSENT"
            consentsFacade.updateZudConsent(KRYNICA_CONTRACT.contractId,
                    toUpdateZudConsent(KRYNICA_ZUD_CONSENT))
        and: "it is $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
        when: "$KASIA_CONSENT_CORDINATOR marks $KRYNICA_ZUD_CONSENT as sent by mail"
            consentsFacade.markZudConsentAsSentByMail(KRYNICA_CONTRACT.contractId)
        then: "ZUD consent is marked as sent by mail"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [zudConsent: with(KRYNICA_ZUD_CONSENT, [consentStatus: SENT,
                                                                                                                                      mailingDate  : TOMORROW,
                                                                                                                                      deliveryType : DeliveryType.POST])])
    }

    def "should add agreement for ZUD consent"() {
        given: "there is $KRYNICA_CONSENTS stage with required ZUD consent"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$IWONA_CONSENT_COLLECTOR is logged in $TOMORROW"
            instantProvider.useFixedClock(TOMORROW)
            loginUser(IWONA_CONSENT_COLLECTOR)
        "$KASIA_CONSENT_CORDINATOR fill $KRYNICA_ZUD_CONSENT"
            consentsFacade.updateZudConsent(KRYNICA_CONTRACT.contractId,
                    toUpdateZudConsent(KRYNICA_ZUD_CONSENT))
        when: "$IWONA_CONSENT_COLLECTOR adds agreement for $KRYNICA_ZUD_CONSENT"
            FileDto zudConsentAgreement = addZudConsentAgreement(KRYNICA_ZUD_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId)
        then: "$KRYNICA_ZUD_CONSENT_AGREEMENT_SCAN is added"
            zudConsentAgreement == KRYNICA_ZUD_CONSENT_AGREEMENT_METADATA
        and: "$KRYNICA_ZUD_CONSENT is accepted"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [zudConsent: with(CONFIRMED_KRYNICA_ZUD_CONSENT, [consentGivenDate: TOMORROW])])
    }

    def "should not add ZUD consent when is not required"() {
        given: "there is $KRYNICA_CONSENTS stage without required ZUD consent"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION, new ContractFixture().withZudRequired(false))
        expect: "New consent $KRYNICA_ZUD_CONSENT is not added"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONSENTS, [zudConsent: null])
    }


    def "should complete consents collection"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR marked send request for plot extracts as done"
            consentsFacade.requestForLandExtractsSent(KRYNICA_CONTRACT.contractId)
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
        then: "Consents collection is marked as completed"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == COMPLETED_KRYNICA_CONSENTS
    }

    def "should complete consents collection with not required ZUD consent"() {
        given: "there is $KRYNICA_CONSENTS stage"
            addKrynicaContractOnStage(BEGIN_CONSENTS_COLLECTION, new ContractFixture().withZudRequired(false))
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        and: "$KASIA_CONSENT_CORDINATOR marked send request for plot extracts as done"
            consentsFacade.requestForLandExtractsSent(KRYNICA_CONTRACT.contractId)
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
        then: "Consents collection is marked as completed"
            consentsFacade.getConsents(KRYNICA_CONTRACT.contractId) == with(COMPLETED_KRYNICA_CONSENTS, [zudConsent: null])
    }
}
