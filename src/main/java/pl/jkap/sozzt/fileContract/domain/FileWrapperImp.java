package pl.jkap.sozzt.fileContract.domain;

import org.springframework.stereotype.Component;


import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileWrapperImp implements FileWrapper {

    @Override
    public Boolean checkFileExist(String pathFile) {
        return Files.exists(Paths.get(pathFile));
    }

}
