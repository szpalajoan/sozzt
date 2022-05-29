package pl.jkap.sozzt.contract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import pl.jkap.sozzt.contract.model.FileContract;
import pl.jkap.sozzt.contract.service.FileContractService;


@RestController
@RequiredArgsConstructor
public class FileContractController {

    private final FileContractService fileContractService;


    @PostMapping(value = "/fileContract", consumes = {"multipart/form-data"})
    public FileContract uploadFileContact(@RequestPart("file") MultipartFile file, @RequestParam long idContract) {
        return fileContractService.uploadFileContact(file,idContract);

    }
}
