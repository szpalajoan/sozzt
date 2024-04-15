package pl.jkap.sozzt.filestorage.domain;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public class FileStorageFacade {

    private final FileSystemStorage fileSystemStorage;

    public FileStorageFacade(FileSystemStorage fileSystemStorage) {
        this.fileSystemStorage = fileSystemStorage;
        this.fileSystemStorage.init();
    }

    public String store(MultipartFile file, UUID idContract, FileType fileType){
        return fileSystemStorage.store(file, idContract, fileType);
    }

    public Resource loadAsResource(String filename){
        return fileSystemStorage.loadAsResource(filename);
    }
}
