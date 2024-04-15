package pl.jkap.sozzt.filestorage.domain

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

trait FileSample {

    MultipartFile KRYNICA_CONTRACT_SCAN = new MockMultipartFile("src/test/resources/contract/KRYNICA_CONTRACT.pdf")
}