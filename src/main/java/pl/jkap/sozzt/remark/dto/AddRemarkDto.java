package pl.jkap.sozzt.remark.dto;

import lombok.*;
import pl.jkap.sozzt.remark.domain.RemarkContractStep;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddRemarkDto {
    private UUID remarkId;
    private UUID contractId;
    private RemarkContractStep remarkContractStep;
    private String title;
    private String description;
    private String assignedTo;
    private Instant deadline;
}
