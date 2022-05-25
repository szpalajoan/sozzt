package pl.jkap.sozzt.Contract.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.Contract.model.FileContract;
import pl.jkap.sozzt.Contract.repository.FileContractRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileContractService {

    private final FileContractRepository fileContractRepository;


    public FileContract uploadFileContact(MultipartFile file, long idContract) {

        FileContract fileContract = new FileContract();

        try {
            String savedPathFile = saveFileOnServer(file, idContract);

            fileContract.setPathFile(savedPathFile);
            fileContract.setNameFile(file.getOriginalFilename());
            fileContract.setIdContract(idContract);
            fileContractRepository.save(fileContract);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContract;
    }

    public static String saveFileOnServer(MultipartFile file, long numberFolder) throws IOException {

        FileToAdd fileToAdd = new FileToAdd();

        String pathFileWithName = fileToAdd.createPathWithUniqueFileName(numberFolder,file);
        Files.createDirectories(Paths.get("files/" + numberFolder));
        Files.write(Paths.get(pathFileWithName), file.getBytes());
        return pathFileWithName;
    }




}
