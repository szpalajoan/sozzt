package pl.jkap.sozzt.filestorage.exception;

public class FileAlreadyExistsException extends StorageException {

    public FileAlreadyExistsException(String message) {
        super(message);
    }
}
