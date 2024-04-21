package pl.jkap.sozzt.filestorage.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddContractScanFileDto {

    private UUID fileId;
    private MultipartFile file;
    private UUID contractId;

    public Optional<UUID> getFileId() {
        return Optional.of(fileId);
    }
}
