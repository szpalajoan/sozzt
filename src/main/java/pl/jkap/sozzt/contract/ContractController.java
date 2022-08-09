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

import java.util.List;

@RestController
@RequiredArgsConstructor
@SuppressWarnings(value = "unused")
public class ContractController {

    @Autowired
    private final ContractFacade contractFacade;

    @GetMapping("/contract/{id}")
    public ResponseEntity<ContractDto> getContract(@PathVariable long id) {
        return new ResponseEntity<>(contractFacade.getContract(id), HttpStatus.OK);
    }

    @PostMapping("/contract")
    public ContractDto addContract(@RequestBody ContractDto contractDto) {
        return contractFacade.addContract(contractDto);
    }

    @PutMapping("/contract/confirm_step/{id}")
    public ResponseEntity<ContractDto> confirmContractStep(@PathVariable long id) {
        return new ResponseEntity<>(contractFacade.confirmStep(id), HttpStatus.OK);
    }

    @GetMapping("/contract")
    public List<ContractDto> getContracts(@RequestParam(required = false) int page) {
        int pageNumber = page >= 0 ? page : 0;
        return contractFacade.getContracts(pageNumber);
    }
}
