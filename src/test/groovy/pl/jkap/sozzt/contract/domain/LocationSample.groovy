package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.LocationDto

trait LocationSample {
    LocationDto KRYNICA_LOCATION = LocationDto.builder()
            .region("KRYNICA")
            .city("Muszyna")
            .district("Nowosądecki")
            .transformerStationNumberWithCircuit("Muszyna Wapienne [8295] obw . nr 2")
            .googleMapLink("https://maps.app.goo.gl/BLYvgG6upvYEC9Uu7") //TODO: wywal to
            .fieldNumber("2596/1")
            .build()

    LocationDto KRYNICA_LOCATION_EDITED = LocationDto.builder()
            .region("KRYNICA II")
            .city("Muszyna Mała")
            .district("Nowosądecki II")
            .transformerStationNumberWithCircuit("Muszyna Wapienne [8295] obw . nr 4")
            .googleMapLink("https://maps.app.goo.gl/BLYvg1232322") //TODO: wywal to
            .fieldNumber("2596/2")
            .build()
}