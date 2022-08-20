package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractFacade contractFacade;

    @GetMapping("/contract/{id}")
    public ResponseEntity<ContractDto> getContract(@PathVariable long id) {
        return new ResponseEntity<>(contractFacade.getContract(id), HttpStatus.OK);
    }

    @PostMapping("/contract")
    public ResponseEntity<ContractDto> addContract(@RequestBody ContractDto contractDto) {
        return new ResponseEntity<>(contractFacade.addContract(contractDto), HttpStatus.CREATED);
    }

    @PutMapping("/contract/confirm_step/{id}")
    public ResponseEntity<ContractDto> confirmContractStep(@PathVariable long id) {
        return new ResponseEntity<>(contractFacade.confirmStep(id), HttpStatus.OK);
    }

    @GetMapping("/contract")
    public ResponseEntity<List<ContractDto>> getContracts(@RequestParam(defaultValue = "0") @Min(0) int page) {
        return new ResponseEntity<>(contractFacade.getContracts(page), HttpStatus.OK);
    }
}
