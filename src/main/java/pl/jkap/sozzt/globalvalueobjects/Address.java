package pl.jkap.sozzt.globalvalueobjects;

import lombok.Getter;

@Getter
public class Address {
    private final String street;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String postalCode;
    private final String city;

    private Address(String street, String houseNumber, String apartmentNumber, String postalCode, String city) {
        this.street = validateNonEmpty(street, "Street cannot be empty");
        this.houseNumber = validateNonEmpty(houseNumber, "House number cannot be empty");
        this.apartmentNumber = apartmentNumber != null && !apartmentNumber.isBlank() ? apartmentNumber : null;
        this.postalCode = validatePostalCode(postalCode);
        this.city = validateNonEmpty(city, "City cannot be empty");
    }

    public static Address of(String street, String houseNumber, String apartmentNumber, String postalCode, String city) {
        return new Address(street, houseNumber, apartmentNumber, postalCode, city);
    }

    public static Address of(String street, String houseNumber, String postalCode, String city) {
        return new Address(street, houseNumber, null, postalCode, city);
    }

    private static String validateNonEmpty(String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value.trim();
    }

    private static String validatePostalCode(String postalCode) {
        if (postalCode == null || !postalCode.matches("\\d{2}-\\d{3}")) {
            throw new IllegalArgumentException("Invalid postal code format. Expected format: XX-XXX");
        }
        return postalCode;
    }
}
