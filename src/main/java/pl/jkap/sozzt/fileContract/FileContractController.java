package pl.jkap.sozzt.fileContract;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.fileContract.domain.FileContractFacade;
import pl.jkap.sozzt.fileContract.domain.FileType;

@RestController
@RequiredArgsConstructor
public class FileContractController {

    @Autowired
    private final FileContractFacade fileContractFacade;

    @PostMapping(value = "/fileContract", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadFileContact(@RequestPart("file") MultipartFile file, @RequestParam long idContract, @RequestParam FileType fileType) {
        fileContractFacade.store(file, idContract, fileType);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
