package pl.jkap.sozzt.projectpurposesmappreparation

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.documentation.domain.RouteDrawingSample
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto
import pl.jkap.sozzt.projectpurposesmappreparation.dto.RouteDrawingDto
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait ProjectPurposesMapPreparationSample implements ContractSample, RouteDrawingSample {

    ProjectPurposesMapPreparationDto KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION = ProjectPurposesMapPreparationDto.builder()
            .projectPurposesMapPreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(65))
            .isGeodeticMapUploaded(false)
            .routeDrawing(NOT_STARTED_ROUTE_DRAWING)
            .build()

    ProjectPurposesMapPreparationDto COMPLETED_KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION = ProjectPurposesMapPreparationDto.builder()
            .projectPurposesMapPreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(65))
            .isGeodeticMapUploaded(true)
            .correctnessOfTheMap(true)
            .routeDrawing()
            .build()

    ProjectPurposesMapPreparationDto with(ProjectPurposesMapPreparationDto projectPurposesMapPreparationDto, Map<String, Object> properties) {
        return SampleModifier.with(ProjectPurposesMapPreparationDto.class, projectPurposesMapPreparationDto, properties)
    }
}