package pl.jkap.sozzt.filestorage.domain;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.filestorage.exception.StorageException;
import pl.jkap.sozzt.filestorage.exception.StorageFileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

class FileSystemStorage {
    private static final String UPLOAD_FILE_DIR = "upload_dir/";
    private final Path rootLocation;

    //private final FileWrapper fileWrapper;

    FileSystemStorage() { //FileWrapper fileWrapper
        //this.fileWrapper = fileWrapper;
        this.rootLocation = Paths.get(UPLOAD_FILE_DIR);
    }

    String store(MultipartFile file, String path) {
        String pathFile;
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file ");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileName.contains("..")) {
            throw new StorageException("Cannot store file with relative path outside current directory " + fileName);
        }
        try {
            String directoriesFile = UPLOAD_FILE_DIR + path;
            pathFile = directoriesFile + createUniqueFileName(fileName, directoriesFile);
            Files.createDirectories(Paths.get(directoriesFile));
            Files.write(Paths.get(pathFile), file.getBytes());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }
        return pathFile;
    }

    private String createUniqueFileName(String fileName, String directoriesFile) {
        int additionalNameDistinction = 1;

        StringBuilder fileNameBuilder = new StringBuilder(fileName);
        while (checkFileExist(directoriesFile + fileNameBuilder)) { //fileWrapper.
            fileNameBuilder = new StringBuilder( fileName +" (" + additionalNameDistinction + ")"  );
            additionalNameDistinction++;
        }
        return fileNameBuilder.toString();
    }


    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    Resource loadAsResource(String filename) {
        try {
            Path file = load(1 + "/" + filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    //TODO tety
    public boolean checkFileExist(String pathFile) {
        return Files.exists(Paths.get(pathFile));
    }
}
