package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;

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

    //todo dodać innego dtoa
    @PutMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto editContract(@PathVariable UUID idContract, @RequestBody ContractDto editContractDto) {
        return contractFacade.editContract(idContract, editContractDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractDto> getContracts() {
        return contractFacade.getContracts();
    }

}
