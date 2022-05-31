package pl.jkap.sozzt.contract.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.contract.model.FileContract;
import pl.jkap.sozzt.contract.model.FileType;
import pl.jkap.sozzt.contract.repository.FileContractRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileContractService {

    private final FileContractRepository fileContractRepository;
    private final FileWrapper fileWrapper;

    public FileContract uploadFileContact(MultipartFile file, long idContract, FileType fileType) {

        AddingFile addingFile = new AddingFile(fileWrapper);
        try {
            addingFile.setFile(file);
            addingFile.setNumberFolder(idContract);
            addingFile.saveFileOnServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileContract fileContract = FileMapper.mapToFileContract(addingFile);
        fileContract.setFileType(fileType);
        fileContractRepository.save(fileContract);
        return fileContract;
    }

}
