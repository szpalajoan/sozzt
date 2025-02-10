package pl.jkap.sozzt.remark.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditRemarkDto {
    private UUID remarkId;
    private String title;
    private String description;
    private String assignedTo;
    private Instant deadline;
}
