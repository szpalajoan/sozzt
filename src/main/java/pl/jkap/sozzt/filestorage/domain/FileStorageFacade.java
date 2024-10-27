package pl.jkap.sozzt.filestorage.domain;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.filestorage.event.ContractScanDeletedEvent;
import pl.jkap.sozzt.filestorage.event.PreliminaryMapDeletedEvent;
import pl.jkap.sozzt.filestorage.event.PreliminaryMapUploadedEvent;
import pl.jkap.sozzt.filestorage.exception.FileAlreadyExistsException;
import pl.jkap.sozzt.filestorage.exception.FileNotFoundException;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class FileStorageFacade {

    private final ContractSecurityFacade contractSecurityFacade;
    private final FileSystemStorage fileSystemStorage;
    private final FileRepository fileRepository;
    private final FileEventPublisher fileEventPublisher;

    @Builder
    FileStorageFacade(ContractSecurityFacade contractSecurityFacade,
                      FileSystemStorage fileSystemStorage,
                      FileRepository fileRepository,
                      FileEventPublisher fileEventPublisher) {
        this.contractSecurityFacade = contractSecurityFacade;
        this.fileSystemStorage = fileSystemStorage;
        this.fileSystemStorage.init();
        this.fileRepository = fileRepository;
        this.fileEventPublisher = fileEventPublisher;
    }

    public List<FileDto> getFiles(UUID objectId, FileType fileType){
        return fileRepository
                .findByObjectIdAndFileType(objectId, fileType)
                .stream()
                .map(File::dto)
                .toList();
    }

    public FileDto addContractScan(AddFileDto addContractScanDto) {
        contractSecurityFacade.checkCanAddContractScan(addContractScanDto.getObjectId());
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
        contractSecurityFacade.checkCanAddPreliminaryMap(addPreliminaryMapFileDto.getObjectId());
        checkPreliminaryMapNotExists(addPreliminaryMapFileDto);
        File addedFile = addFile(
                addPreliminaryMapFileDto.getFileId().orElseGet(UUID::randomUUID),
                addPreliminaryMapFileDto.getFile(),
                FileType.PRELIMINARY_MAP,
                addPreliminaryMapFileDto.getObjectId()
        );
        return addedFile.dto();
    }

    private void checkPreliminaryMapNotExists(AddFileDto addPreliminaryMapFileDto) {
        if(fileRepository.existsByObjectIdAndFileType(addPreliminaryMapFileDto.getObjectId(), FileType.PRELIMINARY_MAP)) {
            throw new FileAlreadyExistsException("Preliminary map already exists");
        }
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
        publishDeletedFileEvent(file);
        fileRepository.delete(file);
    }

    private void publishDeletedFileEvent(File file) {
        switch (file.getFileType()) {
            case CONTRACT_SCAN_FROM_TAURON:
                fileEventPublisher.contractScanDeleted(new ContractScanDeletedEvent(file.getObjectId()));
                break;
            case PRELIMINARY_MAP:
                fileEventPublisher.preliminaryMapDeleted(new PreliminaryMapDeletedEvent(file.getObjectId()));
                break;
        }
    }
}
