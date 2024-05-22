package pl.jkap.sozzt.filestorage.domain;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.filestorage.exception.FileException;
import pl.jkap.sozzt.filestorage.exception.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

class FileSystemStorage {
    private static final String UPLOAD_FILE_DIR = "upload_dir/";
    private final Path rootLocation;

    FileSystemStorage() {
        this.rootLocation = Paths.get(UPLOAD_FILE_DIR);
    }

    String store(MultipartFile file, String path) {
        String pathFile;
        if (file.isEmpty()) {
            throw new FileException("Failed to store empty file ");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileName.contains("..")) {
            throw new FileException("Cannot store file with relative path outside current directory " + fileName);
        }
        try {
            String directoriesFile = UPLOAD_FILE_DIR + path;
            pathFile = directoriesFile + createUniqueFileName(fileName, directoriesFile);
            Files.createDirectories(Paths.get(directoriesFile));
            Files.write(Paths.get(pathFile), file.getBytes());
        } catch (IOException e) {
            throw new FileException("Failed to store file " + fileName, e);
        }
        return pathFile;
    }

    private String createUniqueFileName(String fileName, String directoriesFile) {
        int additionalNameDistinction = 1;

        StringBuilder fileNameBuilder = new StringBuilder(fileName);
        while (checkFileExist(directoriesFile + fileNameBuilder)) {
            fileNameBuilder = new StringBuilder( fileName +" (" + additionalNameDistinction + ")"  );
            additionalNameDistinction++;
        }
        return fileNameBuilder.toString();
    }


    Path loadAsResource(String filename) {
        if (!checkFileExist(filename)) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        return rootLocation.resolve(filename.substring(UPLOAD_FILE_DIR.length()));
    }

    void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileException("Could not initialize storage", e);
        }
    }

    private boolean checkFileExist(String pathFile) {
        return Files.exists(Paths.get(pathFile));
    }

    void delete(String path) {
        if (!checkFileExist(path)) {
            throw new FileNotFoundException("File not found: " + path);
        }
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            throw new FileException("Could not delete file " + path, e);
        }
    }
}
