package pl.jkap.sozzt.contract;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
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
import org.springframework.web.server.ResponseStatusException;
import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.exception.ContractNotFoundException;
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContractController {

    @Autowired
    private final ContractFacade contractFacade;

    @GetMapping("/contracts/{id}")
    public ContractDto getContract(@PathVariable long id) {
        return contractFacade.getContract(id);
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
        } catch (ContractNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NotYetImplementedException e) {
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, e.getMessage());
        }
    }

    @GetMapping("/contracts")
    public List<ContractDto> getContracts(@RequestParam(required = false) int page) {
        int pageNumber = page >= 0 ? page : 0;
        return contractFacade.getContracts(pageNumber);
    }
}
