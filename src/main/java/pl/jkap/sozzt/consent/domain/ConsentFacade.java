package pl.jkap.sozzt.consent.domain;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.config.application.ConsentSpringEventPublisher;
import pl.jkap.sozzt.consent.dto.AddConsentDto;
import pl.jkap.sozzt.consent.dto.ConsentDto;
import pl.jkap.sozzt.consent.exception.ConsentNotFoundException;
import pl.jkap.sozzt.file.event.ConsentConfirmationFileSpringEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;


@AllArgsConstructor
public class ConsentFacade {
    private static final int PAGE_SIZE = 5;
    private final ConsentRepository consentRepository;
    private final ConsentCreator consentCreator;
    private final ConsentSpringEventPublisher consentSpringEventPublisher;

    public ConsentDto addConsent(AddConsentDto addConsentDto) {
        requireNonNull(addConsentDto);
        Consent consent = consentCreator.createConsent(addConsentDto);
        return consentRepository.save(consent)
                .dto();
    }

    public ConsentDto getConsent(UUID id) {
        return consentRepository.findById(id)
                .orElseThrow(ConsentNotFoundException::new)
                .dto();
    }

    public List<ConsentDto> getContracts(int page) {
        return consentRepository.findAll(PageRequest.of(page, PAGE_SIZE))
                .stream()
                .map(Consent::dto)
                .collect(Collectors.toList());
    }

    @EventListener
    public void confirmConsentListener(ConsentConfirmationFileSpringEvent consentConfirmationFileSpringEvent) {
        confirmConsent(consentConfirmationFileSpringEvent.getIdConsent());
    }

    private void confirmConsent(UUID idConsent) {
        Consent consent = consentRepository.findById(idConsent)
                .orElseThrow(ConsentNotFoundException::new);
        consent.confirmConsent();
        consentRepository.save(consent);

        if (consentRepository.findByIdContract(consent.getIdContract())
                .stream()
                .allMatch(consentToCheck -> consentToCheck.getStatus()
                        .equals(ConsentStatus.ACCEPTED))) {
            consentSpringEventPublisher.allConsentAccepted(consent.getIdContract());
        }
    }

    public ConsentDto rejectConsent(UUID idConsent, String comment) {
        Consent consent = consentRepository.findById(idConsent)
                .orElseThrow(ConsentNotFoundException::new);
        consent.rejectConsent();
        consent.setComment(comment);
        consentSpringEventPublisher.consentReject(consent.getIdContract());
        return consentRepository.save(consent)
                .dto();
    }
}
