package pl.jkap.sozzt.consents.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.consents.domain.ConsentsFacade;
import pl.jkap.sozzt.consents.dto.*;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/consents/")
@SuppressWarnings("unused")
public class ConsentsController {

    private final ConsentsFacade consentsFacade;

    @PostMapping("{idContract}/begin-consents-collection")
    @ResponseStatus(HttpStatus.CREATED)
    public void beginConsentsCollection(@PathVariable UUID idContract) {
        consentsFacade.beginConsentsCollection(idContract);
    }

    @PutMapping("{idContract}/request-for-land-extracts-sent")
    @ResponseStatus(HttpStatus.OK)
    public void requestForLandExtractsSent(@PathVariable UUID idContract) {
        consentsFacade.requestForLandExtractsSent(idContract);
    }

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

    @PutMapping("{idContract}/private-plot-owner-consent/{privatePlotOwnerConsentId}/agreement")
    @ResponseStatus(HttpStatus.OK)
    public void addPrivatePlotOwnerConsentAgreement(@PathVariable UUID idContract, @PathVariable UUID privatePlotOwnerConsentId, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).additionalObjectId(privatePlotOwnerConsentId).build();
        consentsFacade.addPrivatePlotOwnerConsentAgreement(idContract, privatePlotOwnerConsentId, addFileDto);
    }

    @PutMapping("{idContract}/public-plot-owner-consent/{publicPlotOwnerConsentId}/agreement")
    @ResponseStatus(HttpStatus.OK)
    public void addPublicOwnerConsentAgreement(@PathVariable UUID idContract, @PathVariable UUID publicPlotOwnerConsentId, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).additionalObjectId(publicPlotOwnerConsentId).build();
        consentsFacade.addPublicOwnerConsentAgreement(idContract, publicPlotOwnerConsentId, addFileDto);
    }

    @PutMapping("{idContract}/private-plot-owner-consent/{privatePlotOwnerConsentId}/invalidate")
    @ResponseStatus(HttpStatus.OK)
    public void invalidatePrivatePlotOwnerConsent(@PathVariable UUID idContract, @PathVariable UUID privatePlotOwnerConsentId, @RequestBody InvalidatePrivatePlotOwnerConsentDto invalidatePrivatePlotOwnerConsentDto) {
        consentsFacade.invalidatePrivatePlotOwnerConsent(idContract, privatePlotOwnerConsentId, invalidatePrivatePlotOwnerConsentDto.getReason());
    }

    @PutMapping("{idContract}/public-plot-owner-consent/{publicPlotOwnerConsentId}/invalidate")
    @ResponseStatus(HttpStatus.OK)
    public void invalidatePublicOwnerConsent(@PathVariable UUID idContract, @PathVariable UUID publicPlotOwnerConsentId, @RequestBody InvalidatePublicPlotOwnerConsentDto invalidatePublicPlotOwnerConsentDto) {
        consentsFacade.invalidatePublicPlotOwnerConsent(idContract, publicPlotOwnerConsentId, invalidatePublicPlotOwnerConsentDto.getReason());
    }

    @PutMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeConsentsCollection(@PathVariable UUID idContract) {
        consentsFacade.completeConsentsCollection(idContract);
    }
}
