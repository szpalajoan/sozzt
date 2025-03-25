package pl.jkap.sozzt.consents.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.consents.domain.ConsentsFacade;
import pl.jkap.sozzt.consents.dto.*;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/consents/")
@SuppressWarnings("unused")
public class ConsentsController {

    private final ConsentsFacade consentsFacade;
    private final FileStorageFacade fileStorageFacade;

    @PostMapping("{idContract}/private-plot-owner-consent")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPrivatePlotOwnerConsent(@PathVariable UUID idContract,@RequestBody AddPrivatePlotOwnerConsentDto addPrivatePlotOwnerConsentDto) {
        consentsFacade.addPrivatePlotOwnerConsent(idContract, addPrivatePlotOwnerConsentDto);
    }

    @PostMapping("{idContract}/public-plot-owner-consent")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPublicOwnerConsent(@PathVariable UUID idContract,@RequestBody AddPublicPlotOwnerConsentDto addPublicPlotOwnerConsentDto) {
        consentsFacade.addPublicOwnerConsent(idContract, addPublicPlotOwnerConsentDto);
    }

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public ConsentsDto getConsents(@PathVariable UUID idContract) {
        return consentsFacade.getConsents(idContract);
    }

    @PutMapping("{idContract}/private-plot-owner-consent/{privatePlotOwnerConsentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePrivatePlotOwnerConsent(@PathVariable UUID idContract, @PathVariable UUID privatePlotOwnerConsentId, @RequestBody UpdatePrivatePlotOwnerConsentDto updatePrivatePlotOwnerConsentDto) {
        consentsFacade.updatePrivatePlotOwnerConsent(idContract, privatePlotOwnerConsentId, updatePrivatePlotOwnerConsentDto);
    }

    @PutMapping("{idContract}/public-plot-owner-consent/{publicPlotOwnerConsentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePublicOwnerConsent(@PathVariable UUID idContract, @PathVariable UUID publicPlotOwnerConsentId, @RequestBody UpdatePublicPlotOwnerConsentDto updatePublicPlotOwnerConsentDto) {
        consentsFacade.updatePublicOwnerConsent(idContract, publicPlotOwnerConsentId, updatePublicPlotOwnerConsentDto);
    }

    @PostMapping("{idContract}/private-plot-owner-consent/{privatePlotOwnerConsentId}/agreement")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPrivatePlotOwnerConsentAgreement(@PathVariable UUID idContract, @PathVariable UUID privatePlotOwnerConsentId, @RequestParam("file") MultipartFile file) {
        consentsFacade.addPrivatePlotOwnerConsentAgreement(idContract, privatePlotOwnerConsentId, AddFileDto.builder()
                .file(file)
                .build());
    }

    @PostMapping("{idContract}/public-plot-owner-consent/{publicPlotOwnerConsentId}/agreement")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPublicOwnerConsentAgreement(@PathVariable UUID idContract, @PathVariable UUID publicPlotOwnerConsentId, @RequestParam("file") MultipartFile file) {
        consentsFacade.addPublicOwnerConsentAgreement(idContract, publicPlotOwnerConsentId, AddFileDto.builder()
                .file(file)
                .build());
    }

    @PostMapping("{idContract}/private-plot-owner-consent/{privatePlotOwnerConsentId}/sent-by-mail")
    @ResponseStatus(HttpStatus.OK)
    public void markPrivatePlotOwnerConsentAsSentByMail(@PathVariable UUID idContract, @PathVariable UUID privatePlotOwnerConsentId) {
        consentsFacade.markPrivatePlotOwnerConsentAsSentByMail(idContract, privatePlotOwnerConsentId);
    }

    @PostMapping("{idContract}/public-plot-owner-consent/{publicPlotOwnerConsentId}/sent-by-mail")
    @ResponseStatus(HttpStatus.OK)
    public void markPublicPlotOwnerConsentAsSentByMail(@PathVariable UUID idContract, @PathVariable UUID publicPlotOwnerConsentId) {
        consentsFacade.markPublicPlotOwnerConsentAsSentByMail(idContract, publicPlotOwnerConsentId);
    }

    @PostMapping("{idContract}/private-plot-owner-consent/{privatePlotOwnerConsentId}/invalidate")
    @ResponseStatus(HttpStatus.OK)
    public void invalidatePrivatePlotOwnerConsent(@PathVariable UUID idContract, @PathVariable UUID privatePlotOwnerConsentId, @RequestBody InvalidatePrivatePlotOwnerConsentDto invalidatePrivatePlotOwnerConsentDto) {
        consentsFacade.invalidatePrivatePlotOwnerConsent(idContract, privatePlotOwnerConsentId, invalidatePrivatePlotOwnerConsentDto.getReason());
    }

    @PostMapping("{idContract}/public-plot-owner-consent/{publicPlotOwnerConsentId}/invalidate")
    @ResponseStatus(HttpStatus.OK)
    public void invalidatePublicPlotOwnerConsent(@PathVariable UUID idContract, @PathVariable UUID publicPlotOwnerConsentId, @RequestBody InvalidatePublicPlotOwnerConsentDto invalidatePublicPlotOwnerConsentDto) {
        consentsFacade.invalidatePublicPlotOwnerConsent(idContract, publicPlotOwnerConsentId, invalidatePublicPlotOwnerConsentDto.getReason());
    }

    @PostMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeConsentsCollection(@PathVariable UUID idContract) {
        consentsFacade.completeConsentsCollection(idContract);
    }

    @PutMapping("{idContract}/zud-consent")
    @ResponseStatus(HttpStatus.OK)
    public void updateZudConsent(@PathVariable UUID idContract, @RequestBody UpdateZudConsentDto updateZudConsentDto) {
        consentsFacade.updateZudConsent(idContract, updateZudConsentDto);
    }

    @PostMapping("{idContract}/zud-consent/sent-by-mail")
    @ResponseStatus(HttpStatus.OK)
    public void markZudConsentAsSentByMail(@PathVariable UUID idContract) {
        consentsFacade.markZudConsentAsSentByMail(idContract);
    }

    @PostMapping("{idContract}/zud-consent/agreement")
    @ResponseStatus(HttpStatus.CREATED)
    public void addZudConsentAgreement(@PathVariable UUID idContract, @RequestParam("file") MultipartFile file) {
        consentsFacade.acceptZudConsent(idContract);
        fileStorageFacade.addZudConsentAgreement(AddFileDto.builder()
                .file(file)
                .build());
    }

    @PostMapping("{idContract}/zud-consent/invalidate")
    @ResponseStatus(HttpStatus.OK)
    public void invalidateZudConsent(@PathVariable UUID idContract, @RequestBody InvalidateZudConsentDto invalidateZudConsentDto) {
        consentsFacade.invalidateZudConsent(idContract, invalidateZudConsentDto.getReason());
    }
}
