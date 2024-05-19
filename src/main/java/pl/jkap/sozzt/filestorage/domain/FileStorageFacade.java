package pl.jkap.sozzt.filestorage.domain;


import pl.jkap.sozzt.filestorage.dto.AddContractScanFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.filestorage.event.ContractScanDeletedEvent;
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


    public FileDto addContractScan(AddContractScanFileDto addContractScanFileDto){
        UUID fileId = addContractScanFileDto.getFileId().orElseGet(UUID::randomUUID);
        checkFileNotExists(fileId);
        String path = calculatePath(FileType.CONTRACT_SCAN_FROM_TAURON, addContractScanFileDto.getContractId());
        String savedFilePath = fileSystemStorage.store(addContractScanFileDto.getFile(), path);
        File newfile = File.builder()
                .fileId(fileId)
                .fileName(addContractScanFileDto.getFile().getOriginalFilename())
                .fileType(FileType.CONTRACT_SCAN_FROM_TAURON)
                .objectId(addContractScanFileDto.getContractId())
                .path(savedFilePath)
                .build();
        fileRepository.save(newfile);
        fileEventPublisher.contractScanUploaded(new ContractScanAddedEvent( addContractScanFileDto.getContractId()));
        return newfile.dto();
    }

    public FileDto addPreliminaryMap(AddPreliminaryMapFileDto addPreliminaryMapFileDto) {
        UUID fileId = addPreliminaryMapFileDto.getFileId().orElseGet(UUID::randomUUID);
        checkFileNotExists(fileId);
        String path = calculatePath(FileType.PRELIMINARY_MAP, addPreliminaryMapFileDto.getPreliminaryPlanId());
        String savedFilePath = fileSystemStorage.store(addPreliminaryMapFileDto.getFile(), path);
        File newfile = File.builder()
                .fileId(fileId)
                .fileName(addPreliminaryMapFileDto.getFile().getOriginalFilename())
                .fileType(FileType.PRELIMINARY_MAP)
                .objectId(addPreliminaryMapFileDto.getPreliminaryPlanId())
                .path(savedFilePath)
                .build();
        fileRepository.save(newfile);
        fileEventPublisher.preliminaryMapUploaded(new PreliminaryMapUploadedEvent( addPreliminaryMapFileDto.getPreliminaryPlanId()));
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
