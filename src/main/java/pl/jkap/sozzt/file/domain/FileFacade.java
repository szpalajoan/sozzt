package pl.jkap.sozzt.file.domain;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.config.application.ConsentFileSpringEventPublisher;
import pl.jkap.sozzt.config.application.ContractFileSpringEventPublisher;

import java.util.UUID;


public class FileFacade {

    private final FileSystemStorage fileSystemStorage;
    private final ContractFileSpringEventPublisher contractFileSpringEventPublisher;
    private final ConsentFileSpringEventPublisher consentFileSpringEventPublisher;

    public FileFacade(FileSystemStorage fileSystemStorage, ContractFileSpringEventPublisher contractFileSpringEventPublisher, ConsentFileSpringEventPublisher consentFileSpringEventPublisher) {
        this.fileSystemStorage = fileSystemStorage;
        this.contractFileSpringEventPublisher = contractFileSpringEventPublisher;
        this.consentFileSpringEventPublisher = consentFileSpringEventPublisher;
        this.fileSystemStorage.init();
    }

    public String storeContractFileInRepository(MultipartFile file, UUID idContract, FileType fileType) {
        String pathOfSavedFile = fileSystemStorage.storeFile(file, fileSystemStorage.prepareFileContractPath(idContract, fileType));
        sendEventAboutUploadedFileToContractWithGivenType(idContract, fileType);
        return pathOfSavedFile;
    }

    public String storeConsentConfirmationFileInRepository(MultipartFile file, UUID idConsent, UUID idContract) {
        String pathOfSavedFile = fileSystemStorage.storeFile(file, fileSystemStorage.prepareFileConsentPath(idConsent, idContract));
        consentFileSpringEventPublisher.consentConfirmationFileUpload(idConsent);
        return pathOfSavedFile;
    }

    public Resource loadAsResource(String filename) {
        return fileSystemStorage.loadAsResource(filename);
    }

    private void sendEventAboutUploadedFileToContractWithGivenType(UUID idContract, FileType fileType) {
        switch (fileType) {
            case CONTRACT_SCAN_FROM_TAURON:
                contractFileSpringEventPublisher.storeScanFromTauron(idContract);
                break;
            case PRELIMINARY_MAP:
                contractFileSpringEventPublisher.storePreliminaryMap(idContract);
                break;
        }
    }
}
