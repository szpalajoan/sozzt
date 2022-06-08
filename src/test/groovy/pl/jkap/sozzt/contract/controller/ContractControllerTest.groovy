//package pl.jkap.sozzt.contract.controller
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.ObjectWriter
//import com.fasterxml.jackson.databind.SerializationFeature
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.mock.web.MockMultipartFile
//import org.springframework.security.test.context.support.WithMockUser
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.MvcResult
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import pl.jkap.sozzt.contract.domain.Contract
//
//import pl.jkap.sozzt.contract.old.service.FileContractService
//import pl.jkap.sozzt.contract.old.service.stepContract.DataInputStep
//import spock.lang.Specification
//import org.springframework.http.MediaType
//import javax.transaction.Transactional
//import java.nio.charset.Charset
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
//import static pl.jkap.sozzt.contract.old.model.ContractStep.DATA_INPUT
//import static pl.jkap.sozzt.contract.old.model.ContractStep.WAITING_TO_PRELIMINARY
//import static pl.jkap.sozzt.contract.old.model.FileType.CONTRACT_FROM_TAURON
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//
//@Transactional
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ContractControllerTest extends Specification {
//
//
//    @Autowired
//    private MockMvc mockMvc
//
//    @Autowired
//    private ObjectMapper objectMapper
//
//    @Autowired
//    private ContractService contractBasicDataService
//
//    @Autowired
//    private FileContractService fileContractService
//
//
//    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))
//
//
//    def "Tauron sends a new contract which is entered into the system"() {
//
//        when: "Justyna enters the contract data"
//
//        Contract newContractBasicData = new Contract()
//        newContractBasicData.setInvoiceNumber(invoiceNumber)
//        newContractBasicData.setLocation(location)
//
//        MvcResult mvcResult = mockMvc.perform(post("/contract").contentType(APPLICATION_JSON_UTF8)
//                .content(mapperContractBasicDataToJson(newContractBasicData)))
//                .andExpect(MockMvcResultMatchers.status().is(200))
//                .andReturn()
//
//        then: "a new contract is created"
//        Contract contractBasicData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Contract.class)
//
//        contractBasicData.getInvoiceNumber() == invoiceNumber
//        contractBasicData.getLocation() == location
//
//        and: "contract has status 'waiting to preliminary map'"
//        contractBasicData.contractStep == DATA_INPUT
//
//
//        where:
//        location = "Warszawa"
//        invoiceNumber = "123123"
//
//    }
//
//    def "When contract has basic data entered can be sent to preparing preliminary map"() {
//        given: "There is exist contract"
//        Contract newContractBasicData = new Contract()
//        newContractBasicData.setInvoiceNumber(invoiceNumber)
//
//        and: "status of this contract is 'data input'"
//        newContractBasicData.setContractStep(DATA_INPUT)
//        contractBasicDataService.addContract(newContractBasicData)
//
//        and: "an attachment is added to this contract with type 'contract from tauron'"
//        MockMultipartFile file = new MockMultipartFile("file", fileName, MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes())
//        fileContractService.uploadFileContact(file, newContractBasicData.getId(),CONTRACT_FROM_TAURON)
//
//        when: "Justyna confirms that the current step is completed"
//        MvcResult mvcResult = mockMvc.perform(put("/contracts/" +newContractBasicData.getId().toString()+ "/validateStepContract").contentType(APPLICATION_JSON_UTF8)
//                .content(mapperContractBasicDataToJson(newContractBasicData)))
//                .andExpect(MockMvcResultMatchers.status().is(201))
//                .andReturn()
//
//        then: "contract has status 'waiting to verify preliminary uploaded map'"
//        Contract contractBasicData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Contract.class)
//        contractBasicData.getContractStep() == WAITING_TO_PRELIMINARY
//
//        where:
//        invoiceNumber = "123123"
//        fileName = "hello.txt"
//    }
//
//    def "When contract hasn't basic data entered can't be sent to preparing preliminary map"() {
//        given: "There is exist contract without files"
//        Contract newContractBasicData = new Contract()
//        newContractBasicData.setInvoiceNumber(invoiceNumber)
//
//        and: "status of this contract is 'data input'"
//        newContractBasicData.setContractStep(DATA_INPUT)
//        contractBasicDataService.addContract(newContractBasicData)
//
//        when: "Justyna confirms that the current step is completed"
//        MvcResult mvcResult = mockMvc.perform(put("/contracts/" +newContractBasicData.getId().toString()+ "/validateStepContract").contentType(APPLICATION_JSON_UTF8)
//                .content(mapperContractBasicDataToJson(newContractBasicData)))
//                .andExpect(MockMvcResultMatchers.status().is(406))
//                .andReturn()
//
//        then: "there is information about the lack of attachment with type 'contract from tauron'"
//        mvcResult.getResponse().errorMessage == DataInputStep.INFO_ABOUT_NONE_FILE
//
//        where:
//        invoiceNumber = "123123"
//        fileName = "hello.txt"
//    }
//
//
//    private static String mapperContractBasicDataToJson(Contract newContractBasicData) {
//        ObjectMapper mapper = new ObjectMapper()
//        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
//        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter()
//        String requestJson = ow.writeValueAsString(newContractBasicData)
//        requestJson
//    }
//
//
//}
