package pl.jkap.sozzt.basicDataContract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.basicDataContract.model.ContractBasicData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.basicDataContract.model.FileContractData;
import pl.jkap.sozzt.basicDataContract.service.ContractBasicDataService;
import pl.jkap.sozzt.basicDataContract.service.FileContractService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class FileContractController {

    private final FileContractService fileContractService;


    @PostMapping(value = "/fileContract", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadFileContact(@RequestPart("file") MultipartFile file, @RequestParam long idContractBasicData) {
        return fileContractService.uploadFileContact(file,idContractBasicData);

    }
}
