package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractStepDto
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait ContractStepSample implements InstantSamples{

    ContractStepDto KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP = with(ContractStepDto.builder().build(),
            [contractStepType  : ContractStepDto.ContractStepTypeDto.PRELIMINARY_PLAN,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.IN_PROGRESS,
             deadline          : (NOW + Duration.ofDays(3))
            ])

    ContractStepDto KRYNICA_CONTRACT_TERRAIN_VISION_STEP = with(ContractStepDto.builder().build(),
            [contractStepType  : ContractStepDto.ContractStepTypeDto.TERRAIN_VISION,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.ON_HOLD,
             deadline          : (NOW + Duration.ofDays(21))
            ])

    ContractStepDto KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP = with(ContractStepDto.builder().build(),
            [contractStepType  : ContractStepDto.ContractStepTypeDto.PROJECT_PURPOSES_MAP_PREPARATION,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.NOT_ACTIVE,
             deadline          : (NOW + Duration.ofDays(65))
            ])

    ContractStepDto KRYNICA_ROUTE_PREPARATION_STEP = with(ContractStepDto.builder().build(),
            [contractStepType  : ContractStepDto.ContractStepTypeDto.ROUTE_PREPARATION,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.ON_HOLD,
             deadline          : (NOW + Duration.ofDays(70))
            ])

    ContractStepDto KRYNICA_LAND_EXTRACTS_STEP = with(ContractStepDto.builder().build(),
            [contractStepType  : ContractStepDto.ContractStepTypeDto.LAND_EXTRACTS,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.NOT_ACTIVE,
             deadline          : (NOW + Duration.ofDays(70))
            ])

    ContractStepDto KRYNICA_CONSENTS_COLLECTION_STEP = with(ContractStepDto.builder().build(),
            [contractStepType  : ContractStepDto.ContractStepTypeDto.CONSENTS_COLLECTION,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.ON_HOLD,
             deadline          : (NOW + Duration.ofDays(79))
            ])

    ContractStepDto KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP = with(ContractStepDto.builder().build(),
            [contractStepType  : ContractStepDto.ContractStepTypeDto.PREPARATION_OF_DOCUMENTATION,
             contractStepStatus: ContractStepDto.ContractStepStatusDto.ON_HOLD,
             deadline          : (NOW + Duration.ofDays(183))
            ])

    ContractStepDto with(ContractStepDto contractStepDto, Map<String, Object> properties) {
        return SampleModifier.with(ContractStepDto.class, contractStepDto, properties)
    }


}