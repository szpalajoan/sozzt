package pl.jkap.sozzt.contract.old.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Getter
@Setter
public class AddingFile {

    private MultipartFile file;
    private long numberFolder;
    private String savedPathFile;
    private final FileWrapper fileWrapper;

    public AddingFile(FileWrapper fileWrapper) {
        this.fileWrapper = fileWrapper;
    }

    public void saveFileOnServer() throws IOException {
        savedPathFile = createPathWithUniqueFileName();
        Files.createDirectories(Paths.get(NAME_FOLDER_WITH_FILES + numberFolder));
        Files.write(Paths.get(savedPathFile), file.getBytes());
    }

    private String createPathWithUniqueFileName() {
        int additionalNameDistinction = 1;
        String pathFile = createPathNameFromData();


        while(fileWrapper.checkFileExist(pathFile)){
            pathFile = createPathNameFromData(additionalNameDistinction);
            additionalNameDistinction++;
        }
        return pathFile;
    }

    private static final String NAME_FOLDER_WITH_FILES = "files/";

    private String createPathNameFromData(int additionalNameDistinction){
        return NAME_FOLDER_WITH_FILES + numberFolder  + "/(" + additionalNameDistinction + ")" + file.getOriginalFilename();
    }

    private String createPathNameFromData(){
        return NAME_FOLDER_WITH_FILES + numberFolder  + "/"  + file.getOriginalFilename();
    }


}
