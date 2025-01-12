package pl.jkap.sozzt.remark.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.remark.dto.RemarkDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
class Remark {

    private UUID remarkId;
    private UUID contractId;
    private RemarkContractStep remarkContractStep;
    private String title;
    private String description;
    private String createdBy;
    private Instant createdAt;
    private String assignedTo;
    private Instant deadline;
    private RemarkStatus remarkStatus;

    void markAsInProgress() {
        remarkStatus = RemarkStatus.IN_PROGRESS;
    }

    void markAsDone() {
        remarkStatus = RemarkStatus.DONE;
    }

    public void cancel() {
        remarkStatus = RemarkStatus.CANCELED;
    }

    RemarkDto dto() {
        return RemarkDto.builder()
                .remarkId(remarkId)
                .contractId(contractId)
                .remarkContractStep(remarkContractStep)
                .title(title)
                .description(description)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .assignedTo(assignedTo)
                .deadline(deadline)
                .remarkStatus(remarkStatus)
                .build();
    }



}
