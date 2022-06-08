package pl.jkap.sozzt.contract.old.service;

import pl.jkap.sozzt.fileContract.domain.FileContract;

public class FileMapper {
    public static FileContract mapToFileContract(AddingFile addingFile) {
        FileContract fileContract = new FileContract();
        fileContract.setPathFile(addingFile.getSavedPathFile());
        fileContract.setNameFile(addingFile.getFile().getOriginalFilename());
        fileContract.setIdContract(addingFile.getNumberFolder());
        return fileContract;
    }
}
