package pl.jkap.sozzt.remark.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.remark.dto.EditRemarkDto;
import pl.jkap.sozzt.remark.dto.RemarkDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
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

    void cancel() {
        remarkStatus = RemarkStatus.CANCELLED;
    }

    void update(EditRemarkDto editRemarkDto) {
        title = editRemarkDto.getTitle();
        description = editRemarkDto.getDescription();
        assignedTo = editRemarkDto.getAssignedTo();
        deadline = editRemarkDto.getDeadline();
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
