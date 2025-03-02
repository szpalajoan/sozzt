package pl.jkap.sozzt.documentation.domain

import pl.jkap.sozzt.documentation.dto.ConsentsVerificationDto
import pl.jkap.sozzt.documentation.dto.DocumentCompilationDto
import pl.jkap.sozzt.documentation.dto.TauronCommunicationDto
import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.COMPLETED_CONSENTS_COLLECTION

class DocumentationSpec extends SozztSpecification {

    def "should add preparation of documentation when consents collection is completed"() {
        when: "Consents preparation  is completed"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        then: "Preparation of documentation is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == KRYNICA_DOCUMENTATION
    }

    def "should complete consents verification and move to document compilation"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR is logged in"
            loginUser(KASIA_CONSENT_CORDINATOR)
        when: "$KASIA_CONSENT_CORDINATOR completes consents verification"
            documentationFacade.completeConsentsVerification(KRYNICA_CONTRACT.contractId)
        then: "Consents verification is completed"
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_DOCUMENTATION, [
                    consentsVerification: new ConsentsVerificationDto(true),
                    documentCompilation: new DocumentCompilationDto()])
    }

    def "should upload a compiled document and move to tauron communication"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR completes consents verification"
            loginUser(KASIA_CONSENT_CORDINATOR)
            documentationFacade.completeConsentsVerification(KRYNICA_CONTRACT.contractId)
        and: "$WOJTEK_DESIGNER is logged in"
            loginUser(WOJTEK_DESIGNER)
        when: "$WOJTEK_DESIGNER uploads a compiled document to documentation"
            FileDto fileDto = uploadCompiledDocument(KRYNICA_COMPILED_DOCUMENT, KRYNICA_CONTRACT.contractId)
        then: "Compiled document is uploaded to documentation"
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_DOCUMENTATION, [consentsVerification: new ConsentsVerificationDto(true),
                                                                                                              documentCompilation: new DocumentCompilationDto(fileDto.fileId),
                                                                                                              tauronCommunication: new TauronCommunicationDto()]) //TODO tu zakończyłem i dodaj TauronCommunicationSample
    }

    def "should mark printed documentation sent to tauron as done"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR completes consents verification"
            loginUser(KASIA_CONSENT_CORDINATOR)
            documentationFacade.completeConsentsVerification(KRYNICA_CONTRACT.contractId)
        and: "$WOJTEK_DESIGNER uploads a compiled document to documentation"
            loginUser(WOJTEK_DESIGNER)
            uploadCompiledDocument(KRYNICA_COMPILED_DOCUMENT, KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER marks printed documentation sent to tauron as done"
            documentationFacade.markPrintedDocumentationSentToTauronAsDone(KRYNICA_CONTRACT.contractId)
        then: "Printed documentation sent to tauron is marked as done"
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_DOCUMENTATION, [consentsVerification: new ConsentsVerificationDto(true),
                                                                                                              documentCompilation : new DocumentCompilationDto(KRYNICA_COMPILED_DOCUMENT_METADATA.fileId),
                                                                                                              tauronCommunication : new TauronCommunicationDto(isPrintedDocumentationSentToTauron: true)])
    }

    def "should add approve from tauron"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR completes consents verification"
            loginUser(KASIA_CONSENT_CORDINATOR)
            documentationFacade.completeConsentsVerification(KRYNICA_CONTRACT.contractId)
        and: "$WOJTEK_DESIGNER uploads a compiled document to documentation"
            loginUser(WOJTEK_DESIGNER)
            uploadCompiledDocument(KRYNICA_COMPILED_DOCUMENT, KRYNICA_CONTRACT.contractId)
            loginUser(MARCIN_TERRAIN_VISIONER)
        and: "$MARCIN_TERRAIN_VISIONER marks printed documentation sent to tauron as done"
            documentationFacade.markPrintedDocumentationSentToTauronAsDone(KRYNICA_CONTRACT.contractId)
        when: "$MARCIN_TERRAIN_VISIONER adds approve from tauron"
            documentationFacade.addTauronApprove(KRYNICA_CONTRACT.contractId)
        then: "Approve from tauron is added"
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_DOCUMENTATION, [consentsVerification: new ConsentsVerificationDto(true),
                                                                                                              documentCompilation : new DocumentCompilationDto(KRYNICA_COMPILED_DOCUMENT_METADATA.fileId),
                                                                                                              tauronCommunication : new TauronCommunicationDto(isPrintedDocumentationSentToTauron: true, isApprovedByTauron: true)])
    }

    def "should move to term verification"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR completes consents verification"
            loginUser(KASIA_CONSENT_CORDINATOR)
            documentationFacade.completeConsentsVerification(KRYNICA_CONTRACT.contractId)
        and: "$WOJTEK_DESIGNER uploads a compiled document to documentation"
            loginUser(WOJTEK_DESIGNER)
            uploadCompiledDocument(KRYNICA_COMPILED_DOCUMENT, KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER marks printed documentation sent to tauron as done"
            loginUser(MARCIN_TERRAIN_VISIONER)
            documentationFacade.markPrintedDocumentationSentToTauronAsDone(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER adds approve from tauron"
            documentationFacade.addTauronApprove(KRYNICA_CONTRACT.contractId)
        when: "$MARCIN_TERRAIN_VISIONER chooses person responsible for term verification"
            documentationFacade.choosePersonResponsibleForTermVerification(KRYNICA_CONTRACT.contractId, MONIKA_CONTRACT_INTRODUCER.name)
        then: "Person responsible for term verification is chosen and term verification is started"
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_DOCUMENTATION, [consentsVerification: new ConsentsVerificationDto(true),
                                                                                                              documentCompilation : new DocumentCompilationDto(KRYNICA_COMPILED_DOCUMENT_METADATA.fileId),
                                                                                                              tauronCommunication : new TauronCommunicationDto(isPrintedDocumentationSentToTauron: true, isApprovedByTauron: true),
                                                                                                              termVerification    : KRYNICA_TERM_VERIFICATION])
    }

    def "should approve that all terms are actual"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR completes consents verification"
            loginUser(KASIA_CONSENT_CORDINATOR)
            documentationFacade.completeConsentsVerification(KRYNICA_CONTRACT.contractId)
        and: "$WOJTEK_DESIGNER uploads a compiled document to documentation"
            loginUser(WOJTEK_DESIGNER)
            uploadCompiledDocument(KRYNICA_COMPILED_DOCUMENT, KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER marks printed documentation sent to tauron as done"
            loginUser(MARCIN_TERRAIN_VISIONER)
            documentationFacade.markPrintedDocumentationSentToTauronAsDone(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER adds approve from tauron"
            documentationFacade.addTauronApprove(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER chooses person responsible for term verification"
            documentationFacade.choosePersonResponsibleForTermVerification(KRYNICA_CONTRACT.contractId, MONIKA_CONTRACT_INTRODUCER.name)
        when: "$MONIKA_CONTRACT_INTRODUCER approves that all terms are actual"
            documentationFacade.approveThatAllTermsAreActual(KRYNICA_CONTRACT.contractId)
        then: "Terms are approved"
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_DOCUMENTATION, [consentsVerification: new ConsentsVerificationDto(true),
                                                                                                              documentCompilation : new DocumentCompilationDto(KRYNICA_COMPILED_DOCUMENT_METADATA.fileId),
                                                                                                              tauronCommunication : new TauronCommunicationDto(isPrintedDocumentationSentToTauron: true, isApprovedByTauron: true),
                                                                                                              termVerification    : with(KRYNICA_TERM_VERIFICATION, [areAllTermsActual: true])])
    }

    def "should complete documentation"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$KASIA_CONSENT_CORDINATOR completes consents verification"
            loginUser(KASIA_CONSENT_CORDINATOR)
            documentationFacade.completeConsentsVerification(KRYNICA_CONTRACT.contractId)
        and: "$WOJTEK_DESIGNER uploads a compiled document to documentation"
            loginUser(WOJTEK_DESIGNER)
            uploadCompiledDocument(KRYNICA_COMPILED_DOCUMENT, KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER marks printed documentation sent to tauron as done"
            loginUser(MARCIN_TERRAIN_VISIONER)
            documentationFacade.markPrintedDocumentationSentToTauronAsDone(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER adds approve from tauron"
            documentationFacade.addTauronApprove(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER chooses person responsible for term verification"
            documentationFacade.choosePersonResponsibleForTermVerification(KRYNICA_CONTRACT.contractId, MONIKA_CONTRACT_INTRODUCER.name)
        and: "$MONIKA_CONTRACT_INTRODUCER approves that all terms are actual"
            documentationFacade.approveThatAllTermsAreActual(KRYNICA_CONTRACT.contractId)
        when: "$MONIKA_CONTRACT_INTRODUCER completes documentation"
            documentationFacade.completeDocumentation(KRYNICA_CONTRACT.contractId)
        then: "Documentation is completed"
            documentationFacade.getDocumentation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_DOCUMENTATION, [consentsVerification: new ConsentsVerificationDto(true),
                                                                                                              documentCompilation : new DocumentCompilationDto(KRYNICA_COMPILED_DOCUMENT_METADATA.fileId),
                                                                                                              tauronCommunication : new TauronCommunicationDto(isPrintedDocumentationSentToTauron: true, isApprovedByTauron: true),
                                                                                                              termVerification    : COMPLETED_KRYNICA_TERM_VERIFICATION])
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_PREPARATION_OF_DOCUMENTATION_STEP
    }

}
