package pl.jkap.sozzt.documentation.domain

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.documentation.dto.ConsentsVerificationDto
import pl.jkap.sozzt.documentation.dto.DocumentationDto
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait DocumentationSample implements ContractSample {

    DocumentationDto KRYNICA_DOCUMENTATION = DocumentationDto.builder()
            .documentationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(183))
            .consentsVerification(new ConsentsVerificationDto(false))
            .build()

    DocumentationDto with(DocumentationDto documentationDto, Map<String, Object> properties) {
        return SampleModifier.with(DocumentationDto.class, documentationDto, properties)
    }
}