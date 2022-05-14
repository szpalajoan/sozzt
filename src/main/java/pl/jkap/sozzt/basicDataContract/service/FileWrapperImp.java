package pl.jkap.sozzt.basicDataContract.service;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWrapperImp implements FileWrapper{
    @Override
    public Boolean checkFileExist(String pathFile) {
        return Files.exists(Paths.get(pathFile));
    }
}
