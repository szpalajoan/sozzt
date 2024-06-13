package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContactController {

    private final ContractFacade contractFacade;

    @GetMapping("/contracts/{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto getContract(@PathVariable UUID idContract) {
        return contractFacade.getContract(idContract);
    }

    @PostMapping("/contracts")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto addContract(@RequestBody CreateContractDto addContractDto) {
        return contractFacade.addContract(addContractDto);
    }

}
