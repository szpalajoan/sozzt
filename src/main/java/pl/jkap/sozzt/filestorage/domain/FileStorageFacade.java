package pl.jkap.sozzt.filestorage.domain;

import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.filestorage.event.ContractScanDeletedEvent;
import pl.jkap.sozzt.filestorage.event.PreliminaryMapUploadedEvent;
import pl.jkap.sozzt.filestorage.exception.FileAlreadyExistsException;
import pl.jkap.sozzt.filestorage.exception.FileNotFoundException;

import java.nio.file.Path;
import java.util.UUID;

public class FileStorageFacade {

    private final FileSystemStorage fileSystemStorage;
    private final FileRepository fileRepository;
    private final FileEventPublisher fileEventPublisher;

    FileStorageFacade(FileSystemStorage fileSystemStorage,
                      FileRepository fileRepository,
                      FileEventPublisher fileEventPublisher) {
        this.fileSystemStorage = fileSystemStorage;
        this.fileSystemStorage.init();
        this.fileRepository = fileRepository;
        this.fileEventPublisher = fileEventPublisher;
    }

    public FileDto addContractScan(AddFileDto addContractScanDto) {
        File addedFile = addFile(
                addContractScanDto.getFileId().orElseGet(UUID::randomUUID),
                addContractScanDto.getFile(),
                FileType.CONTRACT_SCAN_FROM_TAURON,
                addContractScanDto.getObjectId()
        );
        fileEventPublisher.contractScanUploaded(new ContractScanAddedEvent(addedFile.getObjectId()));
        return addedFile.dto();
    }

    public FileDto addPreliminaryMap(AddFileDto addPreliminaryMapFileDto) {
        File addedFile = addFile(
                addPreliminaryMapFileDto.getFileId().orElseGet(UUID::randomUUID),
                addPreliminaryMapFileDto.getFile(),
                FileType.PRELIMINARY_MAP,
                addPreliminaryMapFileDto.getObjectId()
        );
        fileEventPublisher.preliminaryMapUploaded(new PreliminaryMapUploadedEvent(addedFile.getObjectId()));
        return addedFile.dto();
    }

    private File addFile(UUID fileId, MultipartFile file, FileType fileType, UUID objectId) {
        checkFileNotExists(fileId);
        String path = calculatePath(fileType, objectId);
        String savedFilePath = fileSystemStorage.store(file, path);
        File newFile = File.builder()
                .fileId(fileId)
                .fileName(file.getOriginalFilename())
                .fileType(fileType)
                .objectId(objectId)
                .path(savedFilePath)
                .build();
        fileRepository.save(newFile);
        publishEvent(objectId, fileType);
        return newFile;
    }

    private void publishEvent(UUID objectId, FileType fileType) {
        switch (fileType) {
            case CONTRACT_SCAN_FROM_TAURON:
                fileEventPublisher.contractScanUploaded(new ContractScanAddedEvent(objectId));
                break;
            case PRELIMINARY_MAP:
                fileEventPublisher.preliminaryMapUploaded(new PreliminaryMapUploadedEvent(objectId));
                break;
        }
    }

    private String calculatePath(FileType fileType, UUID contractId) {
        return  contractId + "/" + fileType + "/";
    }

    private void checkFileNotExists(UUID fileId) {
        if (fileRepository.existsById(fileId)) {
            throw new FileAlreadyExistsException("File with id " + fileId + " already exists");
        }
    }

    public Path downloadFile(UUID fileId){
        File file = fileRepository.findById(fileId).orElseThrow(() -> new FileNotFoundException("File with id " + fileId + " not found"));
        return fileSystemStorage.loadAsResource(file.getPath());
    }

    public void deleteFile(UUID fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new FileNotFoundException("File with id " + fileId + " not found"));
        fileSystemStorage.delete(file.getPath());
        fileRepository.delete(file);
        fileEventPublisher.contractScanDeleted(new ContractScanDeletedEvent(file.getObjectId()));
    }
}
