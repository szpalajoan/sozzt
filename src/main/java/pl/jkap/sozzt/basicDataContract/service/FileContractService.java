package pl.jkap.sozzt.basicDataContract.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.basicDataContract.model.FileContractData;
import pl.jkap.sozzt.basicDataContract.repository.FileContractRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileContractService {

    private final FileContractRepository fileContractRepository;


    public ResponseEntity<String> uploadFileContact(MultipartFile file, long idContractBasicData) {

        FileContractData fileContractData = new FileContractData();

        try {
            String savedPathFile = saveFileOnServer(file, idContractBasicData);

            fileContractData.setPath_file(savedPathFile);
            fileContractData.setName_file(file.getOriginalFilename());
            fileContractData.setId_contract_basic_data(idContractBasicData);

        } catch (IOException e) {
            e.printStackTrace();
        }
        fileContractRepository.save(fileContractData);
        return new ResponseEntity<>("File added", HttpStatus.OK);
    }

    private String saveFileOnServer(MultipartFile file, long idContractBasicData) throws IOException {
        byte[] bytes = file.getBytes();
        Files.createDirectories(Paths.get("files/" + idContractBasicData));
        int i = 1;
        String pathFile = "files/" + idContractBasicData + "/" + file.getOriginalFilename();
        Path path = Paths.get(pathFile);

        while(Files.exists(path)){
            pathFile = "files/" + idContractBasicData + "/(" + i + ")" + file.getOriginalFilename() ;
            path = Paths.get(pathFile);
            i++;
        }
        Files.write(path, bytes);
        return pathFile;
    }
}
