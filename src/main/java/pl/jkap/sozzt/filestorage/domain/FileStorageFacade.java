package pl.jkap.sozzt.filestorage.domain;


import org.springframework.core.io.Resource;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.filestorage.exception.FileAlreadyExistsException;
import pl.jkap.sozzt.filestorage.dto.AddContractScanFileDto;

import java.util.UUID;


public class FileStorageFacade {

    private final FileSystemStorage fileSystemStorage;
    private final FileRepository fileRepository;
    private final FileContractSpringEventPublisher fileContractSpringEventPublisher;

    public FileStorageFacade(FileSystemStorage fileSystemStorage,
                             FileRepository fileRepository,
                             FileContractSpringEventPublisher fileContractSpringEventPublisher) {
        this.fileSystemStorage = fileSystemStorage;
        this.fileSystemStorage.init();
        this.fileRepository = fileRepository;
        this.fileContractSpringEventPublisher = fileContractSpringEventPublisher;
    }


    public FileDto addContractScan(AddContractScanFileDto addContractScanFileDto){
        UUID fileId = addContractScanFileDto.getFileId().orElseGet(() -> UUID.randomUUID());
        checkFileNotExists(fileId);
        String path = calculatePath(FileType.CONTRACT_SCAN_FROM_TAURON, addContractScanFileDto.getContractId());
        String savedFilePath = fileSystemStorage.store(addContractScanFileDto.getFile(), path);
        File newfile = new File(fileId, addContractScanFileDto.getFile().getOriginalFilename(), FileType.CONTRACT_SCAN_FROM_TAURON, savedFilePath);
        fileRepository.save(newfile);
        fileContractSpringEventPublisher.contractScanUploaded(new ContractScanAddedEvent( addContractScanFileDto.getContractId()));
        return newfile.dto();
    }

    private String calculatePath(FileType fileType, UUID contractId) {
        return  contractId + "/" + fileType + "/";
    }

    private void checkFileNotExists(UUID fileId) {
        if (fileRepository.existsById(fileId)) {
            throw new FileAlreadyExistsException("File with id " + fileId + " already exists");
        }
    }

    public Resource loadAsResource(String filename){
        return fileSystemStorage.loadAsResource(filename);
    }
}
