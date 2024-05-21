package pl.jkap.sozzt.filestorage.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddPreliminaryMapFileDto {

    private UUID fileId;
    private MultipartFile file;
    private UUID preliminaryPlanId;
}
