package pl.jkap.sozzt.basicDataContract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import pl.jkap.sozzt.basicDataContract.model.FileContractData;
import pl.jkap.sozzt.basicDataContract.service.FileContractService;


@RestController
@RequiredArgsConstructor
public class FileContractController {

    private final FileContractService fileContractService;


    @PostMapping(value = "/fileContract", consumes = {"multipart/form-data"})
    public FileContractData uploadFileContact(@RequestPart("file") MultipartFile file, @RequestParam long idContractBasicData) {
        return fileContractService.uploadFileContact(file,idContractBasicData);

    }
}
