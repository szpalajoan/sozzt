package pl.jkap.sozzt.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.xml.bind.v2.TODO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.jkap.sozzt.model.ContractBasicData
import pl.jkap.sozzt.repository.ContractBasicDataRepository
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
    private ContractBasicDataRepository contractBasicDataRepository;


    def "Tauron sends a new contract which is entered into the system"() {

        when: "Justyna enters the contract data"
        ContractBasicData newContractBasicData = new ContractBasicData();
        newContractBasicData.setInvoice_number(invoiceNumber);
        newContractBasicData.setLocation(location);
        contractBasicDataRepository.save(newContractBasicData);

        and: "uploads the document"

        TODO

        then: "a new contract is created"
        MvcResult mvcResult = mockMvc.perform(get("/contract/" + newContractBasicData.getId()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()
        ContractBasicData contractBasicData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContractBasicData.class)

        contractBasicData.getId() == newContractBasicData.getId()
        contractBasicData.getInvoice_number() == invoiceNumber
        contractBasicData.getLocation() == location

        and: "contract has status 'waiting to preliminary map'"

        TODO

        where:
        location = "Warszawa"
        invoiceNumber = "123123"
    }

}
