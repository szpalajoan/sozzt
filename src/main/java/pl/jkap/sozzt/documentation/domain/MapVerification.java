package pl.jkap.sozzt.documentation.domain;

import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.instant.InstantProvider;

import java.time.Instant;

@Getter
class MapVerification {
    private boolean correctnessOfTheMap;
    private String approvedBy;
    private Instant approvedAt;

    void approveCorrectnessOfTheMap(InstantProvider instantProvider) {
        correctnessOfTheMap = true;
        approvedAt = instantProvider.now();
        approvedBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
