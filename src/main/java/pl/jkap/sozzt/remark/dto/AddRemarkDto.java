package pl.jkap.sozzt.remark.dto;

import lombok.*;
import pl.jkap.sozzt.remark.domain.RemarkType;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddRemarkDto {
    private UUID remarkId;
    private UUID contractId;
    private RemarkType remarkType;
    private String title;
    private String description;
    private String assignedTo;
    private Instant deadline;

    public Optional<UUID> getRemarkId() {
        return Optional.ofNullable(remarkId);
    }
}
