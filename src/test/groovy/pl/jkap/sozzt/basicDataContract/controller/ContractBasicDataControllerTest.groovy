package pl.jkap.sozzt.basicDataContract.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.jkap.sozzt.basicDataContract.model.ContractBasicData
import spock.lang.Specification
import org.springframework.http.MediaType
import javax.transaction.Transactional
import java.nio.charset.Charset

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ContractBasicDataControllerTest extends Specification {


    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper


    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))


    def "Tauron sends a new contract which is entered into the system"() {

        when: "Justyna enters the contract data"

        ContractBasicData newContractBasicData = new ContractBasicData()
        newContractBasicData.setInvoice_number(invoiceNumber)
        newContractBasicData.setLocation(location)


        MvcResult mvcResult = mockMvc.perform(post("/contract").contentType(APPLICATION_JSON_UTF8)
                .content(mapperContractBasicDataToJson(newContractBasicData)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()

        then: "a new contract is createsd"
        ContractBasicData contractBasicData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContractBasicData.class)

        contractBasicData.getInvoice_number() == invoiceNumber
        contractBasicData.getLocation() == location

        and: "contract has status 'waiting to preliminary map'"
        contractBasicData.getId_status_contract().getId_status_contract() == waitingToPreliminaryMapStatus


        where:
        location = "Warszawa"
        invoiceNumber = "123123"
        waitingToPreliminaryMapStatus = 1

    }

    private static String mapperContractBasicDataToJson(ContractBasicData newContractBasicData) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter()
        String requestJson = ow.writeValueAsString(newContractBasicData)
        requestJson
    }


}
