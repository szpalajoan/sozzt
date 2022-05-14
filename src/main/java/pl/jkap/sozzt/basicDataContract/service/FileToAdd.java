package pl.jkap.sozzt.basicDataContract.service;

import org.springframework.web.multipart.MultipartFile;

public class FileToAdd {

    public void setFileWrapper(FileWrapper fileWrapper) {
        this.fileWrapper = fileWrapper;
    }

    private FileWrapper fileWrapper;


    private static final String NAME_FOLDER_WITH_FILES = "files/";

    public String createPathWithUniqueFileName(long numberFolder, MultipartFile file) {
        int additionalNameDistinction = 1;
        String pathFile = createPathNameFromData(numberFolder, file.getOriginalFilename());

        while(fileWrapper.checkFileExist(pathFile)){
            pathFile = createPathNameFromData(numberFolder, file.getOriginalFilename(), additionalNameDistinction);
            additionalNameDistinction++;
        }
        return pathFile;
    }

    private static String createPathNameFromData(long numberFolder, String nameFile, int additionalNameDistinction){
        return NAME_FOLDER_WITH_FILES + numberFolder  + "/(" + additionalNameDistinction + ")" + nameFile;
    }

    private static String createPathNameFromData(long numberFolder, String nameFile){
        return NAME_FOLDER_WITH_FILES + numberFolder  + "/"  + nameFile;
    }








}
