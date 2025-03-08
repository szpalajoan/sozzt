package pl.jkap.sozzt.routepreparation

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.routedrawing.dto.MapVerificationDto
import pl.jkap.sozzt.routedrawing.dto.RoutePreparationDto
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait RoutePreparationSample implements ContractSample, RouteDrawingSample {

    RoutePreparationDto KRYNICA_ROUTE_PREPARATION = RoutePreparationDto.builder()
            .routePreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(70))
            .mapVerification(MapVerificationDto.builder().build())
            .routeDrawing(NOT_STARTED_ROUTE_DRAWING)
            .build()

    RoutePreparationDto COMPLETED_KRYNICA_ROUTE_PREPARATION = RoutePreparationDto.builder()
            .routePreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(70))
            .mapVerification(MapVerificationDto.builder().build())
            .routeDrawing(KRYNICA_ROUTE_DRAWING)
            .build()

    RoutePreparationDto with(RoutePreparationDto routePreparationDto, Map<String, Object> properties) {
        return SampleModifier.with(RoutePreparationDto.class, routePreparationDto, properties)
    }
}