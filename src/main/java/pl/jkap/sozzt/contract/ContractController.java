package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.AddContractDto;
import pl.jkap.sozzt.contract.dto.ContractDto;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractFacade contractFacade;

    @GetMapping("/contracts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto getContract(@PathVariable UUID id) {
        return contractFacade.getContract(id);
    }

    @PostMapping("/contracts")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto addContract(@RequestBody AddContractDto addContractDto) {
        return contractFacade.addContract(addContractDto);
    }

    @PutMapping("/contracts/confirm_step/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto confirmContractStep(@PathVariable UUID id) {
        return contractFacade.confirmStep(id);
    }

    @GetMapping("/contracts")
    @ResponseStatus(HttpStatus.OK)
    public List<ContractDto> getContracts(@RequestParam(defaultValue = "0") @Min(0) int page) {
        return contractFacade.getContracts(page);
    }
}
