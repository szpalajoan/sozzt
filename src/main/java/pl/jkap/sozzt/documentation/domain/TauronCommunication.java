package pl.jkap.sozzt.documentation.domain;

import pl.jkap.sozzt.documentation.dto.TauronCommunicationDto;

class TauronCommunication {
    boolean isPrintedDocumentationSentToTauron;
    boolean isApprovedByTauron;


    TauronCommunication() {
        this.isPrintedDocumentationSentToTauron = false;
        this.isApprovedByTauron = false;
    }

    private TauronCommunication(boolean isPrintedDocumentationSentToTauron, boolean isApprovedByTauron) {
        this.isPrintedDocumentationSentToTauron = isPrintedDocumentationSentToTauron;
        this.isApprovedByTauron = isApprovedByTauron;
    }

    TauronCommunication markPrintedDocumentationSentToTauronAsDone() {
        return new TauronCommunication(true, isApprovedByTauron);
    }

    TauronCommunication markApprovedByTauron() {
        return new TauronCommunication(isPrintedDocumentationSentToTauron, true);
    }

    TauronCommunicationDto dto() {
        return TauronCommunicationDto.builder()
                .isPrintedDocumentationSentToTauron(isPrintedDocumentationSentToTauron)
                .isApprovedByTauron(isApprovedByTauron)
                .build();
    }
}
