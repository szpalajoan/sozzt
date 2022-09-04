package pl.jkap.sozzt.file.event;



import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class ContractFileUploadedSpringEvent {
    private final UUID idContract;
}