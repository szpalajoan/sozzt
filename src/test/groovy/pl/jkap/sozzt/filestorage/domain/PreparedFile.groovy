package pl.jkap.sozzt.filestorage.domain

import org.springframework.web.multipart.MultipartFile
import pl.jkap.sozzt.filestorage.dto.FileDto

class PreparedFile {
    FileDto metadata
    MultipartFile fileAsMultipartFile

    PreparedFile(FileDto metadata, MultipartFile fileAsMultipartFile) {
        this.metadata = metadata
        this.fileAsMultipartFile = fileAsMultipartFile
    }
}
