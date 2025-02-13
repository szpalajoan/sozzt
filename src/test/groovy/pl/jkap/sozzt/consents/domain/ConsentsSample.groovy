package pl.jkap.sozzt.consents.domain

import pl.jkap.sozzt.consents.dto.ConsentsDto
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait ConsentsSample implements PlotOwnerConsentSample, ContractSample {


    ConsentsDto KRYNICA_CONSENTS = ConsentsDto.builder()
            .consentId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(79))
            .privatePlotOwnerConsents([])
            .publicOwnerConsents([])
            .zudConsents([])
            .build()

    ConsentsDto COMPLETED_KRYNICA_CONSENTS = ConsentsDto.builder()
            .consentId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(79))
            .requestForPlotExtractsSent(true)
            .privatePlotOwnerConsents([CONFIRMED_KRYNICA_PRIVATE_PLOT_OWNER_CONSENT])
            .publicOwnerConsents([CONFIRMED_PUBLIC_PLOT_OWNER_CONSENT])
            .zudConsent(CONFIRMED_KRYNICA_ZUD_CONSENT)
            .completed(true)
            .build()


    ConsentsDto with(ConsentsDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(ConsentsDto.class, consentsDto, properties)
    }
}