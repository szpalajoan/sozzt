package pl.jkap.sozzt.basicDataContract.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.jkap.sozzt.basicDataContract.model.ContractBasicData
import pl.jkap.sozzt.basicDataContract.model.FileContractData
import pl.jkap.sozzt.basicDataContract.service.ContractBasicDataService
import org.springframework.http.MediaType
import spock.lang.Specification


@SpringBootTest
@AutoConfigureMockMvc
class FileContractControllerTest extends Specification {

    @Autowired
    private ContractBasicDataService contractBasicDataService

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper


    def "Justyna can upload an attachment to the contract"() {

        given: "There is exist contract"
        ContractBasicData newContractBasicData = new ContractBasicData()
        newContractBasicData.setInvoice_number(invoiceNumber)
        contractBasicDataService.addContractBasicData(newContractBasicData)

        when: "Justyna upload an attachment to the contract"
        MockMultipartFile file = new MockMultipartFile("file", fileName, MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes())

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileContract")
                .file(file)
                .param("idContractBasicData", newContractBasicData.getId_contract_basic_data().toString()))
                .andReturn()

        then: "an attachment is added to this contract"
        FileContractData fileContractData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FileContractData.class)
        fileContractData.getName_file() == fileName
        fileContractData.getId_contract_basic_data() == newContractBasicData.getId_contract_basic_data()


        where:
        invoiceNumber = "123123"
        fileName = "hello.txt"

    }

}
