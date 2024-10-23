package pl.jkap.sozzt.filestorage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping("{contractId}/contract-scan")
    public ResponseEntity<FileDto> addContractScan(@PathVariable UUID contractId, @RequestParam("file") MultipartFile file,
            @RequestParam("fileId") UUID fileId) {
        AddFileDto addContractScanDto = AddFileDto.builder()
                .fileId(fileId)
                .file(file)
                .objectId(contractId)
                .build();
        FileDto addedFile = fileStorageFacade.addContractScan(addContractScanDto);
        return ResponseEntity.ok(addedFile);
    }


    @GetMapping("{contractId}/contract-scan")
    public ResponseEntity<List<FileDto>> getContractScans(@PathVariable UUID contractId) {
        List<FileDto> files = fileStorageFacade.getFiles(contractId, FileType.CONTRACT_SCAN_FROM_TAURON);
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
        if (path == null || !path.toFile().exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            Resource resource = new UrlResource(path.toUri());

            String contentType = Files.probeContentType(path);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
