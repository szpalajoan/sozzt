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
public class AddFileDto {

    private UUID fileId;
    private MultipartFile file;
    private UUID contractId;
    private UUID additionalObjectId;
    public Optional<UUID> getFileId() {
        return Optional.ofNullable(fileId);
    }

    public Optional<UUID> getAdditionalObjectId() {
        return Optional.ofNullable(additionalObjectId);
    }
}
