package pl.jkap.sozzt.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.file.domain.FileFacade;
import pl.jkap.sozzt.file.domain.FileType;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class FileController {

    @Autowired
    private final FileFacade fileFacade;

    @PostMapping(value = "/contractFile", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadContractFile(@RequestPart("file") MultipartFile file, @RequestParam UUID idContract, @RequestParam FileType fileType) {
        return fileFacade.storeFileInRepository(file, idContract, fileType);
    }

    @PostMapping(value = "/consentConfirmationFile", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadConsentConfirmationFile(@RequestPart("file") MultipartFile file, @RequestParam UUID idContract, @RequestParam FileType fileType) {
        return fileFacade.storeFileInRepository(file, idContract, fileType);
    }
}
