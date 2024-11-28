package pl.jkap.sozzt.routepreparation

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto
import pl.jkap.sozzt.sample.SampleModifier

import java.time.Duration

trait RoutePreparationSample implements ContractSample {

    RoutePreparationDto KRYNICA_ROUTE_PREPARATION = RoutePreparationDto.builder()
            .routePreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(51))
            .isGeodeticMapUploaded(false)
            .build()

    RoutePreparationDto with(RoutePreparationDto routePreparationDto, Map<String, Object> properties) {
        return SampleModifier.with(RoutePreparationDto.class, routePreparationDto, properties)
    }
}