package pl.jkap.sozzt.basicDataContract.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.basicDataContract.model.FileContractData;
import pl.jkap.sozzt.basicDataContract.repository.FileContractRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileContractService {

    private final FileContractRepository fileContractRepository;


    public FileContractData uploadFileContact(MultipartFile file, long idContractBasicData) {

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
        return fileContractData;
    }

    public static String saveFileOnServer(MultipartFile file, long numberFolder) throws IOException {

        FileToAdd fileToAdd = new FileToAdd();

        String pathFileWithName = fileToAdd.createPathWithUniqueFileName(numberFolder,file);

        Files.write(Paths.get(pathFileWithName), file.getBytes());
        return pathFileWithName;
    }




}
