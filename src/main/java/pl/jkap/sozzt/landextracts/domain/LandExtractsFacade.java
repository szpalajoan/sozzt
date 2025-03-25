package pl.jkap.sozzt.landextracts.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.landextracts.dto.AddLandExtractsDto;
import pl.jkap.sozzt.landextracts.dto.LandExtractsDto;
import pl.jkap.sozzt.landextracts.event.LandExtractsCompletedEvent;
import pl.jkap.sozzt.landextracts.exception.LandExtractsNotFoundException;

import java.util.UUID;

@Builder
@Slf4j
public class LandExtractsFacade {

    private final LandExtractsRepository landExtractsRepository;
    private final LandExtractsEventPublisher landExtractsEventPublisher;
    private final InstantProvider instantProvider;

    public LandExtractsDto getLandExtracts(UUID uuid) {
        return landExtractsRepository.findById(uuid)
                .orElseThrow(() -> new LandExtractsNotFoundException("Land extracts not found: " + uuid))
                .dto();
    }

    public void addLandExtracts(AddLandExtractsDto addLandExtractsDto) {
        LandExtracts newLandExtracts = LandExtracts.create(addLandExtractsDto.getLandExtractsId(), addLandExtractsDto.getDeadline());
        landExtractsRepository.save(newLandExtracts);
        log.info("Land extracts created: {}", newLandExtracts);
    }

    public void requestForLandExtractsSent(UUID uuid) {
        LandExtracts landExtracts = landExtractsRepository.findById(uuid)
                .orElseThrow(() -> new LandExtractsNotFoundException("Land extracts not found: " + uuid));
        landExtracts.requestForLandExtractsSent();
        landExtractsRepository.save(landExtracts);
        log.info("Request for land extracts sent for landExtractsId: {}", landExtracts.getId());
    }

    public void completeLandExtracts(UUID uuid) {
        LandExtracts landExtracts = landExtractsRepository.findById(uuid)
                .orElseThrow(() -> new LandExtractsNotFoundException("Land extracts not found: " + uuid));
        landExtracts.complete();
        landExtractsRepository.save(landExtracts);
        landExtractsEventPublisher.landExtractsCompleted(new LandExtractsCompletedEvent(uuid));
        log.info("Land extracts completed: {}", uuid);
    }
} 