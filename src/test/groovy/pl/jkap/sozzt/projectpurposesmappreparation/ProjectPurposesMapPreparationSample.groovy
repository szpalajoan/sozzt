package pl.jkap.sozzt.projectpurposesmappreparation

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.routepreparation.RouteDrawingSample
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait ProjectPurposesMapPreparationSample implements ContractSample {

    ProjectPurposesMapPreparationDto KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION = ProjectPurposesMapPreparationDto.builder()
            .projectPurposesMapPreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(65))
            .isGeodeticMapUploaded(false)
            .build()

    ProjectPurposesMapPreparationDto COMPLETED_KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION = ProjectPurposesMapPreparationDto.builder()
            .projectPurposesMapPreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(65))
            .isGeodeticMapUploaded(true)
            .build()

    ProjectPurposesMapPreparationDto with(ProjectPurposesMapPreparationDto projectPurposesMapPreparationDto, Map<String, Object> properties) {
        return SampleModifier.with(ProjectPurposesMapPreparationDto.class, projectPurposesMapPreparationDto, properties)
    }
}