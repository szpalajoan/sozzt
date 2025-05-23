package pl.jkap.sozzt.documentation.domain;

import pl.jkap.sozzt.documentation.dto.ConsentsVerificationDto;

class ConsentsVerification {
    boolean consentsVerified;

    static ConsentsVerification notStartedConsentsVerification(){
        return new ConsentsVerification(false);
    }

    private ConsentsVerification(boolean consentsVerified) {
        this.consentsVerified = consentsVerified;
    }

    ConsentsVerificationDto dto() {
        return ConsentsVerificationDto.builder().consentsVerified(consentsVerified).build();
    }

    ConsentsVerification completedConsentsVerification() {
        return new ConsentsVerification(true);
    }
}
