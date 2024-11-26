package pl.jkap.sozzt.sample

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto

import java.time.Duration

trait RoutePreparationSample implements ContractSample {

    RoutePreparationDto KRYNICA_ROUTE_PREPARATION = RoutePreparationDto.builder()
            .routePreparationId(KRYNICA_CONTRACT.contractId)
            .deadline(NOW + Duration.ofDays(51))
            .build()

    RoutePreparationDto with(RoutePreparationDto routePreparationDto, Map<String, Object> properties) {
        return SampleModifier.with(RoutePreparationDto.class, routePreparationDto, properties)
    }
}