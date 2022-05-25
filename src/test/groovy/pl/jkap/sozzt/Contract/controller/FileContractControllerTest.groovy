package pl.jkap.sozzt.Contract.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.jkap.sozzt.Contract.model.Contract
import pl.jkap.sozzt.Contract.model.FileContract
import pl.jkap.sozzt.Contract.service.ContractService
import org.springframework.http.MediaType
import spock.lang.Specification


@SpringBootTest
@AutoConfigureMockMvc
class FileContractControllerTest extends Specification {

    @Autowired
    private ContractService contractBasicDataService

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper


    def "Justyna can upload an attachment to the contract"() {

        given: "There is exist contract"
        Contract newContractBasicData = new Contract()
        newContractBasicData.setInvoiceNumber(invoiceNumber)
        contractBasicDataService.addContract(newContractBasicData)

        when: "Justyna upload an attachment to the contract"
        MockMultipartFile file = new MockMultipartFile("file", fileName, MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes())

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileContract")
                .file(file)
               .param("idContractBasicData", newContractBasicData.getId().toString()))
               .andReturn()

       then: "an attachment is added to this contract"
        FileContract fileContractData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FileContract.class)
        fileContractData.getNameFile() == fileName
        fileContractData.getIdContract() == newContractBasicData.getId()


        where:
        invoiceNumber = "123123"
        fileName = "hello.txt"

    }

}
