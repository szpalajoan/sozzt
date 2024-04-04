package pl.jkap.sozzt.fileContract.domain

import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile


trait FileSample {

    MockMultipartFile SCAN_FROM_TAURON = new MockMultipartFile("Scan from Tauron", "scan_123321", MediaType.TEXT_PLAIN_VALUE, "Example".getBytes())
}