package pl.jkap.sozzt.contract.service


import org.springframework.web.multipart.MultipartFile
import pl.jkap.sozzt.contract.old.service.AddingFile
import pl.jkap.sozzt.contract.old.service.FileWrapper
import spock.lang.Specification


class AddingFileTest extends Specification {

    private MultipartFile multipartFile
    private FileWrapper fileWrapper

    void setup() {
        multipartFile = Stub(MultipartFile.class)
        fileWrapper = Stub(FileWrapper.class)
    }


    def "adding a new file if a file with the same name already exists, it will change to a unique name"() {
        given: "there are files with name "
        multipartFile.getOriginalFilename() >> nameFile
        fileWrapper.checkFileExist("files/1/test.txt") >> fileFirstExist
        fileWrapper.checkFileExist("files/1/(1)test.txt") >> fileSecondExist
        fileWrapper.checkFileExist("files/1/(2)test.txt") >> fileThirdExist

        when: "add new file"
        AddingFile addingFile = new AddingFile(fileWrapper)
        addingFile.setFile(multipartFile)
        addingFile.setNumberFolder(1)
        addingFile.saveFileOnServer()

        then: " if a file with the same name already exists there is unique name"
        addingFile.savedPathFile == newName


        where:
        fileFirstExist | fileSecondExist | fileThirdExist | newName
        false          | false           | false          | "files/1/test.txt"
        true           | false           | false          | "files/1/(1)test.txt"
        true           | true            | false          | "files/1/(2)test.txt"

        nameFile = "test.txt"
    }


}
