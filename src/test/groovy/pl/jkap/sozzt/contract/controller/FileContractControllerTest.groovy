package pl.jkap.sozzt.contract.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.jkap.sozzt.contract.model.Contract
import pl.jkap.sozzt.contract.model.FileContract
import pl.jkap.sozzt.contract.service.ContractService
import org.springframework.http.MediaType

import static pl.jkap.sozzt.contract.model.FileType.CONTRACT_FROM_TAURON
import spock.lang.Specification



import static pl.jkap.sozzt.contract.model.ContractStep.DATA_INPUT


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
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

        and: "contract has status 'data input'"
        newContractBasicData.setContractStep(DATA_INPUT)
        contractBasicDataService.addContract(newContractBasicData)

        when: "Justyna upload an attachment to the contract as contract from tauron"
        MockMultipartFile file = new MockMultipartFile("file", fileName, MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes())

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/fileContract")
                .file(file)
                .param("idContract", newContractBasicData.getId().toString())
                .param("fileType", CONTRACT_FROM_TAURON.toString()))
                .andReturn()

        then: "an attachment is added to this contract"
        FileContract fileContractData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FileContract.class)
        fileContractData.getNameFile() == fileName
        fileContractData.getIdContract() == newContractBasicData.getId()

        and: " attachment have type 'contract from tauron'"
        fileContractData.getFileType() == CONTRACT_FROM_TAURON

        where:
        invoiceNumber = "123123"
        fileName = "hello.txt"

    }

}
