package pl.jkap.sozzt.documentation.domain;

import pl.jkap.sozzt.documentation.dto.DocumentCompilationDto;

import java.util.UUID;

class DocumentCompilation {
    private final UUID compiledDocumentId;

    DocumentCompilation() {
        this.compiledDocumentId = null;
    }
    private DocumentCompilation(UUID compiledDocumentId) {
        this.compiledDocumentId = compiledDocumentId;
    }

    DocumentCompilation withCompiledDocument(UUID compiledDocumentId) {
        return new DocumentCompilation(compiledDocumentId);
    }

    DocumentCompilationDto dto() {
        return new DocumentCompilationDto(compiledDocumentId);
    }


}
