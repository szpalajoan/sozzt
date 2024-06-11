package pl.jkap.sozzt.filestorage.domain

import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.sample.SozztSpecification

import java.nio.file.Files

class PreliminaryPlanFileStorageSpec extends SozztSpecification {

    def "Should add a preliminary map to a preliminary plan"() {
        given: "There is a $KRYNICA_PRELIMINARY_PLAN"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        when: "$DAREK_PRELIMINARY_PLANER uploads $KRYNICA_PRELIMINARY_MAP to $KRYNICA_PRELIMINARY_PLAN"
            FileDto addedPreliminaryMap = uploadPreliminaryMap(KRYNICA_PRELIMINARY_MAP, KRYNICA_PRELIMINARY_PLAN)
        then: "$KRYNICA_PRELIMINARY_MAP is added to $KRYNICA_PRELIMINARY_PLAN"
            addedPreliminaryMap == KRYNICA_PRELIMINARY_MAP_METADATA
            Files.readAllBytes(fileStorageFacade.downloadFile(addedPreliminaryMap.getFileId())) == KRYNICA_PRELIMINARY_MAP_FILE.getBytes()
    }
}
