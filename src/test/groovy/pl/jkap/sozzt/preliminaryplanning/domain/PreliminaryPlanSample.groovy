package pl.jkap.sozzt.preliminaryplanning.domain

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto

import java.time.Duration

trait PreliminaryPlanSample implements ContractSample {

    PreliminaryPlanDto KRYNICA_PRELIMINARY_PLAN = PreliminaryPlanDto.builder()
            .preliminaryPlanId(KRYNICA_CONTRACT.contractId)
            .deadline((KRYNICA_CONTRACT.contractDetails.orderDate + Duration.ofDays(3)))
            .build()
}