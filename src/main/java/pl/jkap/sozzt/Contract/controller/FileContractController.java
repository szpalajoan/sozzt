package pl.jkap.sozzt.Contract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import pl.jkap.sozzt.Contract.model.FileContract;
import pl.jkap.sozzt.Contract.service.FileContractService;


@RestController
@RequiredArgsConstructor
public class FileContractController {

    private final FileContractService fileContractService;


    @PostMapping(value = "/fileContract", consumes = {"multipart/form-data"})
    public FileContract uploadFileContact(@RequestPart("file") MultipartFile file, @RequestParam long idContract) {
        return fileContractService.uploadFileContact(file,idContract);

    }
}
