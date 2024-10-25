package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;
import pl.jkap.sozzt.contract.dto.EditContractDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/")
@SuppressWarnings("unused")
public class ContactController {

    private final ContractFacade contractFacade;

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto getContract(@PathVariable UUID idContract) {
        return contractFacade.getContract(idContract);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto addContract(@RequestBody CreateContractDto addContractDto) {
         return contractFacade.addContract(addContractDto);
    }

    @PutMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto editContract(@PathVariable UUID idContract, @RequestBody EditContractDto editContractDto) {
        ContractDto editedContract = contractFacade.editContract(idContract, editContractDto);
        contractFacade.finalizeContractIntroduction(idContract); //todo dodać to do osobnej metody
        return editedContract;

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractDto> getContracts() {
        return contractFacade.getContracts();
    }

}
