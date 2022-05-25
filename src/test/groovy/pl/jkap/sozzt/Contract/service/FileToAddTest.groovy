package pl.jkap.sozzt.Contract.service


import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification


class FileToAddTest extends Specification {

    private FileToAdd addedFile

    private MultipartFile multipartFile
    private FileWrapper fileWrapper

    void setup() {
        multipartFile = Stub(MultipartFile.class)
        fileWrapper = Stub(FileWrapper.class)

        addedFile = new FileToAdd()
        addedFile.setFileWrapper(fileWrapper)

    }


    def "adding a new file if a file with the same name already exists, it will change to a unique name"() {
        given: "there is file with name "
        multipartFile.getOriginalFilename() >> nameFile
        fileWrapper.checkFileExist("files/1/test.txt") >> fileFirstExist
        fileWrapper.checkFileExist("files/1/(1)test.txt") >> fileSecondExist
        fileWrapper.checkFileExist("files/1/(2)test.txt") >> fileThirdExist

        when: "add new file"
        String resultNewName = addedFile.createPathWithUniqueFileName(1, multipartFile)

        then: " if a file with the same name already exists there is unique name"
        resultNewName == newName


        where:
        fileFirstExist | fileSecondExist | fileThirdExist | newName
        false          | false           | false          | "files/1/test.txt"
        true           | false           | false          | "files/1/(1)test.txt"
        true           | true            | false          | "files/1/(2)test.txt"

        nameFile = "test.txt"
    }


}
