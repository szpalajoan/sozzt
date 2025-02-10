package pl.jkap.sozzt.remark.dto;

import lombok.*;
import pl.jkap.sozzt.remark.domain.RemarkType;
import pl.jkap.sozzt.remark.domain.RemarkStatus;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RemarkDto {
    private UUID remarkId;
    private UUID contractId;
    private RemarkType remarkType;
    private String title;
    private String description;
    private String createdBy;
    private Instant createdAt;
    private String assignedTo;
    private Instant deadline;
    private RemarkStatus remarkStatus;
}
