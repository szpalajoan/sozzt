package pl.jkap.sozzt.file.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.file.domain.FileType;

import java.util.UUID;

@Getter
public class AddContractFileDto {
     UUID idContract;
    FileType fileType;
}
