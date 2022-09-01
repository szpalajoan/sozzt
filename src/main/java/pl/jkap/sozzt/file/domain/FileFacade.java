package pl.jkap.sozzt.file.domain;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.config.application.ContractSpringEventPublisher;

import java.util.UUID;


public class FileFacade {

    private final FileSystemStorage fileSystemStorage;
    private final ContractSpringEventPublisher contractSpringEventPublisher;

    public FileFacade(FileSystemStorage fileSystemStorage, ContractSpringEventPublisher contractSpringEventPublisher) {
        this.fileSystemStorage = fileSystemStorage;
        this.contractSpringEventPublisher = contractSpringEventPublisher;
        this.fileSystemStorage.init();
    }

    public String store(MultipartFile file, UUID idContract, FileType fileType) {
        String pathOfSavedFile = fileSystemStorage.store(file, fileSystemStorage.prepareFileContractPath(idContract, fileType));
        sendEventAboutUploadedFileToContractWithGivenType(idContract, fileType);
        return pathOfSavedFile;
    }


    public Resource loadAsResource(String filename) {
        return fileSystemStorage.loadAsResource(filename);
    }

    private void sendEventAboutUploadedFileToContractWithGivenType(UUID idContract, FileType fileType) {
        switch (fileType) {
            case CONTRACT_SCAN_FROM_TAURON:
                contractSpringEventPublisher.storeScanFromTauron(idContract);
                break;
            case PRELIMINARY_MAP:
                contractSpringEventPublisher.storePreliminaryMap(idContract);
                break;
        }
    }
}
