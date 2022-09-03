package pl.jkap.sozzt.consent.domain

import pl.jkap.sozzt.consent.dto.ConsentDto
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.contract.dto.ContractStepEnum
import pl.jkap.sozzt.contract.dto.DataInputContractDto
import spock.lang.Specification

class ConsentSpec extends Specification implements ConsentSample {

    def "Should add consent"() {

        when: "User add new consent"
        ConsentDto consentDto = consentFacade.addConsent(NEW_CONSENT_IN_TARNOW_TO_ACCEPT)

        then: "New consent has 'WAITING' status"
        consentDto.getStatus() == ConsentStatus.WAITING
    }
}