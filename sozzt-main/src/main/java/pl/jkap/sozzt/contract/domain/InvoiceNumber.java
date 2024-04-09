package pl.jkap.sozzt.contract.domain;

import lombok.Getter;

@Getter
final class InvoiceNumber {

    private final String invoiceNumber;

    InvoiceNumber(String invoiceNumber) {
        if (invoiceNumber == null || invoiceNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice number cannot be null or empty.");
        }
        if (!isValid(invoiceNumber)) {
            throw new IllegalArgumentException("Invalid invoice number format.");
        }
        this.invoiceNumber = invoiceNumber;
    }

    private boolean isValid(String invoiceNumber) {
        return true;
    }
}
