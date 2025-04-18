package pl.jkap.sozzt.globalvalueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.Instant;

@Embeddable
@Getter
public class AuditInfo {

    private final Instant createdAt;
    private final String createdBy;
    private final Instant updatedAt;
    private final String updatedBy;

    public AuditInfo(Instant createdAt, String createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = null;
        this.updatedBy = null;
    }

    private AuditInfo(Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public AuditInfo update(Instant updatedAt, String updatedBy) {
        if (updatedAt.isAfter(createdAt)) {
            return new AuditInfo(createdAt, createdBy, updatedAt, updatedBy);
        } else {
            throw new IllegalArgumentException("Update time must be after creation time.");
        }
    }
}
