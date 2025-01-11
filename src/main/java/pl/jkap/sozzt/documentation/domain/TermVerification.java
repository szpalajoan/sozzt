package pl.jkap.sozzt.documentation.domain;

import pl.jkap.sozzt.documentation.dto.TermVerificationDto;

class TermVerification {
    private final String verifierName;
    private final boolean areAllTermsActual;
    private final boolean areContractsCorrect;

    TermVerification(String verifierName) {
        this.verifierName = verifierName;
        this.areAllTermsActual = false;
        this.areContractsCorrect = false;
    }

    private TermVerification(String verifierName, boolean areAllTermsActual, boolean areContractsCorrect) {
        this.verifierName = verifierName;
        this.areAllTermsActual = areAllTermsActual;
        this.areContractsCorrect = areContractsCorrect;
    }

    TermVerification approveThatAllTermsAreActual() {
        return new TermVerification(verifierName, true, areContractsCorrect);
    }

    TermVerification approveThatContractsAreCorrect() {
        return new TermVerification(verifierName, areAllTermsActual, true);
    }

    TermVerificationDto dto() {
        return new TermVerificationDto(verifierName, areAllTermsActual, areContractsCorrect);
    }
}
