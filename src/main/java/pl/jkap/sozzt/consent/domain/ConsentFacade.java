package pl.jkap.sozzt.consent.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import pl.jkap.sozzt.consent.dto.ConsentDto;
import pl.jkap.sozzt.consent.exception.ConsentNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;


@AllArgsConstructor
public class ConsentFacade {
    private final ConsentRepository consentRepository;
    private final ConsentCreator consentCreator;
    private static final int PAGE_SIZE = 5;


    public ConsentDto addConsent(ConsentDto consentDto) {
        requireNonNull(consentDto);
        Consent consent = consentCreator.createConsent(consentDto);
        return consentRepository.save(consent).dto();
    }

    public ConsentDto getConsent(UUID id) {
        return consentRepository.findById(id).orElseThrow(ConsentNotFoundException::new).dto();
    }

    public List<ConsentDto> getContracts(int page) {
        return consentRepository.findAll(PageRequest.of(page, PAGE_SIZE))
                .stream()
                .map(Consent::dto)
                .collect(Collectors.toList());
    }
}
