package pl.jkap.sozzt.preliminaryplanning.domain

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait PreliminaryPlanSample implements ContractSample {

    String KRYNICA_GOOGLE_MAP_URL = "https://goo.gl/maps/5Y2sR2Hw3KJ4ZaZr7"
    String UPDATED_KRYNICA_GOOGLE_MAP_URL = "https://goo.gl/maps/5Y2sR2Hw3KJ4ZaZr8"

    PreliminaryPlanDto KRYNICA_PRELIMINARY_PLAN = PreliminaryPlanDto.builder()
            .preliminaryPlanId(KRYNICA_CONTRACT.contractId)
            .deadline((KRYNICA_CONTRACT.contractDetails.orderDate + Duration.ofDays(3)))
            .build()

    PreliminaryPlanDto COMPLETED_KRYNICA_PRELIMINARY_PLAN = PreliminaryPlanDto.builder()
            .preliminaryPlanId(KRYNICA_CONTRACT.contractId)
            .deadline((KRYNICA_CONTRACT.contractDetails.orderDate + Duration.ofDays(3)))
            .isPreliminaryMapUploaded(true)
            .googleMapUrl(KRYNICA_GOOGLE_MAP_URL)
            .build()

    PreliminaryPlanDto with(PreliminaryPlanDto preliminaryPlanDto, Map<String, Object> properties) {
        return SampleModifier.with(PreliminaryPlanDto.class, preliminaryPlanDto, properties)
    }
}