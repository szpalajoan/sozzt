package pl.jkap.sozzt.filestorage.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PreliminaryMapUploadedEvent {

    private final UUID preliminaryPlanId;
}
