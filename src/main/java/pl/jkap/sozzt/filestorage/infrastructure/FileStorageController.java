package pl.jkap.sozzt.filestorage.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.filestorage.domain.FileType;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/")
@SuppressWarnings("unused")
public class FileStorageController {

    private final FileStorageFacade fileStorageFacade;

    @PostMapping("{contractId}/contract-scans")
    public ResponseEntity<FileDto> addContractScan(@PathVariable UUID contractId, @RequestParam("file") MultipartFile file) {

        AddFileDto addContractScanDto = AddFileDto.builder().file(file).objectId(contractId).build();
        FileDto addedFile = fileStorageFacade.addContractScan(addContractScanDto);
        return ResponseEntity.ok(addedFile);
    }


    @PostMapping("{contractId}/preliminary-maps")
    public ResponseEntity<FileDto> addPreliminaryMap(@PathVariable UUID contractId, @RequestParam("file") MultipartFile file) {
        AddFileDto addPreliminaryMapFileDto = AddFileDto.builder().file(file).objectId(contractId).build();
        FileDto addedFile = fileStorageFacade.addPreliminaryMap(addPreliminaryMapFileDto);
        return ResponseEntity.ok(addedFile);
    }

    @PostMapping("{contractId}/photos-from-place-of-the-contract")
    public ResponseEntity<FileDto> addPhotoFromPlaceOfTheContract(@PathVariable UUID contractId, @RequestParam("file") MultipartFile file) {
        AddFileDto addPreliminaryMapFileDto = AddFileDto.builder().file(file).objectId(contractId).build();
        FileDto addedFile = fileStorageFacade.addPhotoFromPlaceOfTheContract(addPreliminaryMapFileDto);
        return ResponseEntity.ok(addedFile);
    }

    @PostMapping("{contractId}/preliminary-updated-maps")
    public ResponseEntity<FileDto> addPreliminaryUpdatedMap(@PathVariable UUID contractId, @RequestParam("file") MultipartFile file) {
        AddFileDto addPreliminaryUpdatedMapDto = AddFileDto.builder().file(file).objectId(contractId).build();
        FileDto addedFile = fileStorageFacade.addPreliminaryUpdatedMap(addPreliminaryUpdatedMapDto);
        return ResponseEntity.ok(addedFile);
    }

    @GetMapping("{contractId}/files")
    public ResponseEntity<List<FileDto>> getFiles(@PathVariable UUID contractId, @RequestParam("fileType") FileType fileType) {
        List<FileDto> files = fileStorageFacade.getFiles(contractId, fileType);
        return ResponseEntity.ok(files);
    }

    @DeleteMapping("{contractId}/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable UUID contractId, @PathVariable UUID fileId) {
        fileStorageFacade.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("{contractId}/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable UUID contractId, @PathVariable UUID fileId) {
        Path path = fileStorageFacade.downloadFile(fileId);
        try {
            String contentType = Files.probeContentType(path);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new UrlResource(path.toUri()));

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
