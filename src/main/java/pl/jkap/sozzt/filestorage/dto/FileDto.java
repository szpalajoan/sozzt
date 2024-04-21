package pl.jkap.sozzt.filestorage.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FileDto {
    private UUID fileId;
    private String fileName;
}
