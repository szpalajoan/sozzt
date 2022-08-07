package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;

@RestController
@RequiredArgsConstructor
public class ContractController {

    @Autowired
    private final ContractFacade contractFacade;

    @GetMapping("/contracts")
    public ContractDto getContracts() {
        return contractFacade.getContract(1);
    }

    @PostMapping("/contract")
    public ContractDto addContract(@RequestBody ContractDto contractDto) {
        return contractFacade.addContract(contractDto);
    }

    @PutMapping("/contract/confirm_step/{id}")
    public ResponseEntity<ContractDto> confirmContractStep(@PathVariable long id) {
        try {
            return new ResponseEntity<>(contractFacade.confirmStep(id), HttpStatus.OK);
        } catch (NoScanFileOnConfirmingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
