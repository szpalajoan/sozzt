package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import pl.jkap.sozzt.contract.dto.LocationDto;

@Getter
@Embeddable
class Location {
    private final String region;
    private final String district;
    private final String city;
    private final String transformerStationNumberWithCircuit;
    private final String fieldNumber;
    private final String googleMapLink;

    Location(LocationDto locationDto) {
        if (locationDto.getRegion() == null || locationDto.getRegion().trim().isEmpty()) {
            throw new IllegalArgumentException("Region cannot be null or empty.");
        }
        if (locationDto.getDistrict() == null || locationDto.getDistrict().trim().isEmpty()) {
            throw new IllegalArgumentException("District cannot be null or empty.");
        }
        if (locationDto.getCity() == null || locationDto.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty.");
        }
        if (locationDto.getTransformerStationNumberWithCircuit() == null || locationDto.getTransformerStationNumberWithCircuit().trim().isEmpty()) {
            throw new IllegalArgumentException("Transformer station number with circuit cannot be null or empty.");
        }
        if (locationDto.getFieldNumber() == null || locationDto.getFieldNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Field number cannot be null or empty.");
        }
        if (locationDto.getGoogleMapLink() == null || locationDto.getGoogleMapLink().trim().isEmpty()) {
            throw new IllegalArgumentException("Google map link cannot be null or empty.");
        }

        this.region = locationDto.getRegion();
        this.district = locationDto.getDistrict();
        this.city = locationDto.getCity();
        this.transformerStationNumberWithCircuit = locationDto.getTransformerStationNumberWithCircuit();
        this.fieldNumber = locationDto.getFieldNumber();
        this.googleMapLink = locationDto.getGoogleMapLink();
    }

    LocationDto dto(){
        return LocationDto.builder()
                .city(this.city)
                .district(this.district)
                .fieldNumber(this.fieldNumber)
                .region(this.region)
                .transformerStationNumberWithCircuit(this.transformerStationNumberWithCircuit)
                .googleMapLink(this.googleMapLink)
                .build();
    }
}
