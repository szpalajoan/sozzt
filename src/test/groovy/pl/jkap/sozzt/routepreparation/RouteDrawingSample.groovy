package pl.jkap.sozzt.routepreparation

import pl.jkap.sozzt.routedrawing.dto.RouteDrawingDto
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.user.UserSample

trait RouteDrawingSample implements UserSample {

    RouteDrawingDto NOT_STARTED_ROUTE_DRAWING = RouteDrawingDto.builder().build()

    RouteDrawingDto KRYNICA_ROUTE_DRAWING = RouteDrawingDto.builder()
            .drawingBy(DANIEL_ROUTE_DRAWER.name)
            .mapWithRouteFileId(null)
            .routeWithDataFileId(null)
            .build()

    RouteDrawingDto with(RouteDrawingDto routeDrawingDto, Map<String, Object> properties) {
        return SampleModifier.with(RouteDrawingDto.class, routeDrawingDto, properties)
    }
}
