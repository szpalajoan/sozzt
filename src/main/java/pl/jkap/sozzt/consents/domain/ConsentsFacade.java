package pl.jkap.sozzt.consents.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.consents.dto.*;
import pl.jkap.sozzt.consents.event.ConsentsCollectionCompletedEvent;
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
    private final ConsentsEventPublisher consentsEventPublisher;
    private final InstantProvider instantProvider;



    ConsentsDto getConsents(UUID uuid) {
        return consentsRepository.findById(uuid)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + uuid))
                .dto();
    }

    public void addConsents(AddConsentsDto addConsentsDto) {
        Consents newConsent = Consents.builder()
                .consentId(addConsentsDto.getContractId().orElseGet(UUID::randomUUID))
                .deadline(addConsentsDto.getDeadline())
                .privatePlotOwnerConsents(new ArrayList<>())
                .publicOwnerConsents(new ArrayList<>())
                .zudConsent(addConsentsDto.isZudConsentRequired() ? ZudConsent.requiredEmptyConsent(instantProvider) : null)
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

    public PublicPlotOwnerConsentDto addPublicOwnerConsent(UUID uuid, AddPublicPlotOwnerConsentDto addPublicPlotOwnerConsentDto) {
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

    public void updatePrivatePlotOwnerConsent(UUID consentsId, UUID privatePlotOwnerConsentId, UpdatePrivatePlotOwnerConsentDto updatePrivatePlotOwnerConsentDto) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.updatePrivatePlotOwnerConsent(privatePlotOwnerConsentId, updatePrivatePlotOwnerConsentDto);
        consentsRepository.save(consents);
        log.info("Private plot owner consent updated: {}", updatePrivatePlotOwnerConsentDto);
    }

    public void updatePublicOwnerConsent(UUID consentsId, UUID publicPlotOwnerConsentId, UpdatePublicPlotOwnerConsentDto updatePublicPlotOwnerConsentDto) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.updatePublicPlotOwnerConsent(publicPlotOwnerConsentId, updatePublicPlotOwnerConsentDto);
        consentsRepository.save(consents);
        log.info("Public plot owner consent updated: {}", updatePublicPlotOwnerConsentDto);
    }

    public void markPrivatePlotOwnerConsentAsSentByMail(UUID consentsId, UUID privatePlotOwnerConsentId) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.markPrivatePlotOwnerConsentAsSentByMail(privatePlotOwnerConsentId, instantProvider);
        consentsRepository.save(consents);
        log.info("Private plot owner consent sent by mail: {}", privatePlotOwnerConsentId);
    }

    public void markPublicPlotOwnerConsentAsSentByMail(UUID consentsId, UUID publicPlotOwnerConsentId) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.markPublicPlotOwnerConsentAsSentByMail(publicPlotOwnerConsentId, instantProvider);
        consentsRepository.save(consents);
        log.info("Public plot owner consent sent by mail: {}", publicPlotOwnerConsentId);
    }

    public void invalidatePrivatePlotOwnerConsent(UUID consentsId, UUID privatePlotOwnerConsentId, String reason) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.invalidatePrivatePlotOwnerConsent(privatePlotOwnerConsentId, reason, instantProvider);
        consentsRepository.save(consents);
        log.info("Private plot owner consent invalidated: {}", privatePlotOwnerConsentId);
    }

    public void invalidatePublicPlotOwnerConsent(UUID consentsId, UUID publicPlotOwnerConsentId, String reason) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.invalidatePublicPlotOwnerConsent(publicPlotOwnerConsentId, reason, instantProvider);
        consentsRepository.save(consents);
        log.info("Public plot owner consent invalidated: {}", publicPlotOwnerConsentId);
    }

    public void completeConsentsCollection(UUID consentsId) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.markAsCompleted();
        consentsRepository.save(consents);
        consentsEventPublisher.consentsCollectionCompleted(new ConsentsCollectionCompletedEvent(consentsId));
        log.info("Consents completed: {}", consentsId);
    }

    public void updateZudConsent(UUID consentsId, UpdateZudConsentDto updateZudConsentDto) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.updateZudConsent(updateZudConsentDto);
        consentsRepository.save(consents);
        log.info("ZUD consent updated: {}", updateZudConsentDto);
    }

    public FileDto addZudConsentAgreement(UUID consentsId, AddFileDto addFileDto) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found: " + consentsId));
        consents.acceptZudConsent(instantProvider);
        FileDto addedAgreement = fileStorageFacade.addZudConsentAgreement(addFileDto);
        log.info("ZUD consent accepted for consentsId: {}", consentsId);
        return addedAgreement;
    }

    public void markZudConsentAsSentByMail(UUID consentsId) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.markZudConsentAsSentByMail(instantProvider);
        consentsRepository.save(consents);
        log.info("ZUD consent sent by mail for consentsId: {}", consentsId);
    }

    public void invalidateZudConsent(UUID consentsId, String reason) {
        Consents consents = consentsRepository.findById(consentsId)
                .orElseThrow(() -> new ConsentsNotFoundException("Consents not found for contractId: " + consentsId));
        consents.invalidateZudConsent(reason, instantProvider);
        consentsRepository.save(consents);
        log.info("ZUD consent invalidated for consentsId: {}", consentsId);
    }
}
