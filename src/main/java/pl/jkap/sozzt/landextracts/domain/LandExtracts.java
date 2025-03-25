package pl.jkap.sozzt.landextracts.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.jkap.sozzt.landextracts.dto.LandExtractsDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LandExtracts {
    @Id
    private UUID id;
    private boolean requestForPlotExtractsSent;
    private Instant createdAt;
    private boolean completed;

    static LandExtracts create(UUID contractId, Instant createdAt) {
        return new LandExtracts(contractId, false, createdAt, false);
    }

    void requestForLandExtractsSent() {
        this.requestForPlotExtractsSent = true;
    }

    void complete() {
        this.completed = true;
    }

    LandExtractsDto dto() {
        return LandExtractsDto.builder()
                .landExtractsId(id)
                .requestForPlotExtractsSent(requestForPlotExtractsSent)
                .completed(completed)
                .build();
    }

}