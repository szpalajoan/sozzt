package pl.jkap.sozzt.contract.domain;

import lombok.Getter;

@Getter
class Location {
    private final String location;

    Location(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        this.location = location;
    }

}
