package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractFacade contractFacade;

    @GetMapping("/contracts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto getContract(@PathVariable long id) {
        return contractFacade.getContract(id);
    }

    @PostMapping("/contracts")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto addContract(@RequestBody ContractDto contractDto) {
        return contractFacade.addContract(contractDto);
    }

    @PutMapping("/contracts/confirm_step/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto confirmContractStep(@PathVariable long id) {
        return contractFacade.confirmStep(id);
    }

    @GetMapping("/contracts")
    @ResponseStatus(HttpStatus.OK)
    public List<ContractDto> getContracts(@RequestParam(defaultValue = "0") @Min(0) int page) {
        return contractFacade.getContracts(page);
    }
}
