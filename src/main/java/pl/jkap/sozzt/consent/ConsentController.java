package pl.jkap.sozzt.consent;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.jkap.sozzt.consent.domain.ConsentFacade;
import pl.jkap.sozzt.consent.dto.AddConsentDto;
import pl.jkap.sozzt.consent.dto.ConsentDto;
import pl.jkap.sozzt.consent.dto.RejectConsentDto;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentFacade consentFacade;

    @GetMapping("/consents/{idConsent}")
    @ResponseStatus(HttpStatus.OK)
    public ConsentDto getConsent(@PathVariable UUID idConsent) {
        return consentFacade.getConsent(idConsent);
    }

    @PostMapping("/consents")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsentDto addConsent(@RequestBody AddConsentDto addConsentDto) {
        return consentFacade.addConsent(addConsentDto);
    }

    @GetMapping("/consents")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsentDto> getConsents(@RequestParam(defaultValue = "0") @Min(0) int page) {
        return consentFacade.getContracts(page);
    }

    @PutMapping("/consents/{idConsent}/reject")
    @ResponseStatus(HttpStatus.OK)
    public ConsentDto rejectConsent(@RequestBody RejectConsentDto rejectConsentDto) {
        return consentFacade.rejectConsent(rejectConsentDto.getIdConsent(), rejectConsentDto.getComment());
    }
}
