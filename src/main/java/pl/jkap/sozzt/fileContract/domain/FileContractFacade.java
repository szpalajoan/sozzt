package pl.jkap.sozzt.fileContract.domain;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public class FileContractFacade {

    private final FileSystemStorage fileSystemStorage;


    public FileContractFacade(FileSystemStorage fileSystemStorage) {
        this.fileSystemStorage = fileSystemStorage;
        this.fileSystemStorage.init();
    }

    public String store(MultipartFile file, UUID idContract, FileType fileType) {
        return fileSystemStorage.store(file, idContract, fileType);
    }

    public Resource loadAsResource(String filename) {
        return fileSystemStorage.loadAsResource(filename);
    }
}
