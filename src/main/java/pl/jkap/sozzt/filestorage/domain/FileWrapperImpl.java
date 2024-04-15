package pl.jkap.sozzt.filestorage.domain;

import org.springframework.stereotype.Component;


import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileWrapperImpl implements FileWrapper {

    @Override
    public Boolean checkFileExist(String pathFile) {
        return Files.exists(Paths.get(pathFile));
    }

}
