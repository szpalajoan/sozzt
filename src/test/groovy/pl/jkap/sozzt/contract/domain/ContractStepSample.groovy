package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractStepDto
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait ContractStepSample implements InstantSamples{

    ContractStepDto KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP = with(ContractStepDto.builder().build(),
            [contractStepId    : UUID.fromString("21c4aaa0-4a11-4f83-aa2e-504e23d14495"),
             contractStepType  : ContractStepDto.ContractStepTypeDto.PRELIMINARY_PLAN,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.IN_PROGRESS,
             deadline          : (NOW + Duration.ofDays(3))
            ])

    ContractStepDto KRYNICA_CONTRACT_TERRAIN_VISION_STEP = with(ContractStepDto.builder().build(),
            [contractStepId    : UUID.fromString("21c4aaa0-4a11-4f83-aa2e-504e23d14495"),
             contractStepType  : ContractStepDto.ContractStepTypeDto.TERRAIN_VISION,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.ON_HOLD,
             deadline          : (NOW + Duration.ofDays(21))
            ])

    ContractStepDto with(ContractStepDto contractStepDto, Map<String, Object> properties) {
        return SampleModifier.with(ContractStepDto.class, contractStepDto, properties)
    }


}