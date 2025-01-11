package pl.jkap.sozzt.documentation.domain

import pl.jkap.sozzt.documentation.dto.RouteDrawingDto
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.user.UserSample

trait RouteDrawingSample implements UserSample {

    RouteDrawingDto KRYNICA_ROUTE_DRAWING = RouteDrawingDto.builder()
            .drawingBy(DANIEL_ROUTE_DRAWER.name)
            .mapWithRouteFileId(null)
            .routeWithDataFileId(null)
            .build()

    RouteDrawingDto with(RouteDrawingDto routeDrawingDto, Map<String, Object> properties) {
        return SampleModifier.with(RouteDrawingDto.class, routeDrawingDto, properties)
    }
}
