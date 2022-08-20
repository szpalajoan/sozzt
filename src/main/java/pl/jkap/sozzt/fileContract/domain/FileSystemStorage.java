package pl.jkap.sozzt.fileContract.domain;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.config.application.ContractSpringEventPublisher;
import pl.jkap.sozzt.fileContract.exception.StorageException;
import pl.jkap.sozzt.fileContract.exception.StorageFileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
class FileSystemStorage {
    private static final String UPLOAD_FILE_DIR = "upload_dir/";
    private final Path rootLocation = Paths.get(UPLOAD_FILE_DIR);

    private final ContractSpringEventPublisher contractSpringEventPublisher;

    private final FileWrapper fileWrapper;


    String store(MultipartFile file, UUID idContract, FileType fileType) {

        validateFile(file.isEmpty(), "Failed to store empty file ");
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        validateFile(fileName.contains(".."), "Cannot store file with relative path outside current directory " + fileName);

        String pathFile;
        try {
            String directoriesFile = UPLOAD_FILE_DIR + idContract + "/" + fileType + "/";
            pathFile = directoriesFile + createUniqueFileName(fileName, directoriesFile);
            Files.createDirectories(Paths.get(directoriesFile));
            Files.write(Paths.get(pathFile), file.getBytes());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }
        sendEventAboutUploadedFileWithGivenType(idContract, fileType);
        return pathFile;
    }

    private void sendEventAboutUploadedFileWithGivenType(UUID idContract, FileType fileType) {
        switch (fileType) {
            case CONTRACT_SCAN_FROM_TAURON:
                contractSpringEventPublisher.storeScanFromTauron(idContract);
                break;
            case PRELIMINARY_MAP:
                contractSpringEventPublisher.storePreliminaryMap(idContract);
                break;
        }
    }

    private void validateFile(boolean condition, String throwMessage) {
        if (condition) {
            throw new StorageException(throwMessage);
        }
    }

    private String createUniqueFileName(String fileName, String directoriesFile) {
        int additionalNameDistinction = 1;

        StringBuilder fileNameBuilder = new StringBuilder(fileName);
        while (fileWrapper.checkFileExist(directoriesFile + fileNameBuilder)) {
            fileNameBuilder = new StringBuilder("(" + additionalNameDistinction + ")" + fileName);
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
}
