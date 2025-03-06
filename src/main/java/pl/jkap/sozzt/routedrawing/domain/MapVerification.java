package pl.jkap.sozzt.routedrawing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.routedrawing.dto.MapVerificationDto;

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

    public MapVerificationDto dto() {
        return MapVerificationDto.builder()
                .correctnessOfTheMap(correctnessOfTheMap)
                .verificationDate(verificationDate)
                .build();
    }
}