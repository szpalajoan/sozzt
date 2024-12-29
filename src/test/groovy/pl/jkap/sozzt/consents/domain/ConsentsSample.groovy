package pl.jkap.sozzt.consents.domain

import pl.jkap.sozzt.consents.dto.ConsentsDto
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait ConsentsSample implements ContractSample {


    ConsentsDto KRYNICA_CONSENTS = ConsentsDto.builder()
            .consentId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(51))
            .privatePlotOwnerConsents([])
            .publicOwnerConsents([])
            .build()


    ConsentsDto with(ConsentsDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(ConsentsDto.class, consentsDto, properties)
    }
}