package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.exception.ContractFinalizeException
import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.COMPLETED_CONSENTS_COLLECTION

class CompletePreparationDocumentationSpec extends SozztSpecification {

    def "should complete preparation documentation step"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$MARCIN_TERRAIN_VISIONER approves correctness of the map"
            loginUser(MARCIN_TERRAIN_VISIONER)
            documentationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER chooses person responsible for route drawing"
            documentationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER uploads a drawn route to documentation"
            loginUser(DANIEL_ROUTE_DRAWER)
            uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER uploads a pdf with route and data to documentation"
            uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, KRYNICA_CONTRACT.contractId)
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
        then: "Preparation of documentation is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_PREPARATION_OF_DOCUMENTATION_STEP
    }


    def "should not complete preparation documentation when there are active remarks"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_CONSENTS_COLLECTION)
        and: "$MARCIN_TERRAIN_VISIONER approves correctness of the map"
            loginUser(MARCIN_TERRAIN_VISIONER)
            documentationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER chooses person responsible for route drawing"
            documentationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER uploads a drawn route to documentation"
            loginUser(DANIEL_ROUTE_DRAWER)
            uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER uploads a pdf with route and data to documentation"
            uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, KRYNICA_CONTRACT.contractId)
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
        and: "there is an active remark"
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        when: "trying to complete documentation"
            documentationFacade.completeDocumentation(KRYNICA_CONTRACT.contractId)
        then: "preparation of documentation is not completed"
            thrown(ContractFinalizeException)
    }
}
