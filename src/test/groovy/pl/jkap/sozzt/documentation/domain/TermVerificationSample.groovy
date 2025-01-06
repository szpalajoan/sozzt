package pl.jkap.sozzt.documentation.domain

import pl.jkap.sozzt.documentation.dto.TermVerificationDto
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.user.UserSample

trait TermVerificationSample implements UserSample {

    TermVerificationDto KRYNICA_TERM_VERIFICATION = TermVerificationDto.builder()
            .verifierName(MONIKA_CONTRACT_INTRODUCER.name)
            .areAllTermsActual(false)
            .areContractsCorrect(false)
            .build()

    TermVerificationDto COMPLETED_KRYNICA_TERM_VERIFICATION = TermVerificationDto.builder()
            .verifierName(MONIKA_CONTRACT_INTRODUCER.name)
            .areAllTermsActual(true)
            .areContractsCorrect(true)
            .build()

    TermVerificationDto with(TermVerificationDto termVerificationDto, Map<String, Object> properties) {
        return SampleModifier.with(TermVerificationDto.class, termVerificationDto, properties)
    }

}