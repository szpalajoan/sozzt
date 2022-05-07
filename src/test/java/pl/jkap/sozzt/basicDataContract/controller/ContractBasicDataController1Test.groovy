package pl.jkap.sozzt.basicDataContract.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.xml.bind.v2.TODO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.jkap.sozzt.basicDataContract.model.ContractBasicData
import pl.jkap.sozzt.basicDataContract.service.ContractBasicDataService
import spock.lang.Specification

import javax.transaction.Transactional

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ContractBasicDataController1Test extends Specification {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContractBasicDataService contractBasicDataService;


    def "Tauron sends a new contract which is entered into the system"() {

        when: "Justyna enters the contract data"
        ContractBasicData newContractBasicData = new ContractBasicData();
        newContractBasicData.setInvoice_number(invoiceNumber);
        newContractBasicData.setLocation(location);
        contractBasicDataService.addContractBasicData(newContractBasicData);

        then: "a new contract is created"
        MvcResult mvcResult = mockMvc.perform(get("/contract/" + newContractBasicData.getId_contract_basic_data()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()
        ContractBasicData contractBasicData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContractBasicData.class)

        contractBasicData.getId_contract_basic_data() == newContractBasicData.getId_contract_basic_data()
        contractBasicData.getInvoice_number() == invoiceNumber
        contractBasicData.getLocation() == location

        and: "contract has status 'waiting to preliminary map'"
        contractBasicData.getId_status_contract().getId_status_contract() == 1

        where:
        location = "Warszawa"
        invoiceNumber = "123123"
    }

    def "Justyna can upload an attachment to the contract"(){

        given: "There is exist contract"
        ContractBasicData newContractBasicData = new ContractBasicData();
        newContractBasicData.setInvoice_number(invoiceNumber);
        newContractBasicData.setLocation(location);
        contractBasicDataService.addContractBasicData(newContractBasicData);

        when: "Justyna upload an attachment to the contract"

        then: "an attachment is added"


        where:
        location = "Warszawa"
        invoiceNumber = "123123"
    }

}
