package pl.jkap.sozzt.consents.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.consents.dto.*;
import pl.jkap.sozzt.consents.exception.ConsentsNotFoundException;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.instant.InstantProvider;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Builder
public class ConsentsFacade {

    private final ConsentsRepository consentsRepository;
    private final FileStorageFacade fileStorageFacade;
    private final InstantProvider instantProvider;



    ConsentsDto getConsents(UUID uuid) {
        return consentsRepository.findById(uuid)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + uuid))
                .dto();
    }

    public void addConsents(AddConsentsDto addConsentsDto) {
        Consents newConsent = Consents.builder()
                .consentId(addConsentsDto.getContractId().orElse(UUID.randomUUID()))
                .deadline(addConsentsDto.getDeadline())
                .privatePlotOwnerConsents(new ArrayList<>())
                .publicOwnerConsents(new ArrayList<>())
                .build();
        consentsRepository.save(newConsent);
        log.info("Consents created: {}", newConsent);
    }

    public void requestForLandExtractsSent(UUID uuid) {
        Consents consents = consentsRepository.findById(uuid)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + uuid));
        consents.requestForLandExtractsSent();
        consentsRepository.save(consents);
        log.info("Request for land extracts sent for consentsId: {}", consents.getConsentId());
    }

    public PrivatePlotOwnerConsentDto addPrivatePlotOwnerConsent(UUID uuid, AddPrivatePlotOwnerConsentDto addPrivatePlotOwnerConsentDto) {
        Consents consents = consentsRepository.findById(uuid)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + uuid));
        PrivatePlotOwnerConsent privatePlotOwnerConsent = consents.addPrivatePlotOwnerConsent(addPrivatePlotOwnerConsentDto, instantProvider);
        consentsRepository.save(consents);
        log.info("Private plot owner consent added: {}", addPrivatePlotOwnerConsentDto);
        return privatePlotOwnerConsent.dto();
    }

    public PublicOwnerConsentDto addPublicOwnerConsent(UUID uuid, AddPublicPlotOwnerConsentDto addPublicPlotOwnerConsentDto) {
        Consents consents = consentsRepository.findById(uuid)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + uuid));
        PublicOwnerConsent publicOwnerConsent = consents.addPublicOwnerConsent(addPublicPlotOwnerConsentDto, instantProvider);
        consentsRepository.save(consents);
        log.info("Public plot owner consent added: {}", addPublicPlotOwnerConsentDto);
        return publicOwnerConsent.dto();
    }

    public FileDto addPrivatePlotOwnerConsentAgreement(UUID consentsId, UUID privatePlotOwnerConsentId, AddFileDto addFileDto) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + consentsId));
        consents.acceptPrivatePlotOwnerConsent(privatePlotOwnerConsentId, instantProvider);
        FileDto addedAgreement = fileStorageFacade.addPrivatePlotOwnerConsentAgreement(addFileDto);
        log.info("Private plot owner consent accepted: {}", privatePlotOwnerConsentId);
        return addedAgreement;
    }

    public FileDto addPublicOwnerConsentAgreement(UUID consentsId, UUID publicPlotOwnerConsentId, AddFileDto addFileDto) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + consentsId));
        consents.acceptPublicOwnerConsent(publicPlotOwnerConsentId, instantProvider);
        FileDto addedAgreement = fileStorageFacade.addPublicOwnerConsentAgreement(addFileDto);
        log.info("Public plot owner consent accepted: {}", publicPlotOwnerConsentId);
        return addedAgreement;
    }
}
