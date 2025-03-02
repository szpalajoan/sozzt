package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.jkap.sozzt.instant.InstantProvider;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class MapVerification {
    private boolean correctnessOfTheMap;
    private Instant verificationDate;

    void approveCorrectnessOfTheMap(InstantProvider instantProvider) {
        this.correctnessOfTheMap = true;
        this.verificationDate = instantProvider.now();
    }

    boolean isCompleted() {
        return correctnessOfTheMap;
    }
} 