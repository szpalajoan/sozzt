package pl.jkap.sozzt.documentation.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DocumentationCompletedEvent {
    private final UUID contractId;
}
