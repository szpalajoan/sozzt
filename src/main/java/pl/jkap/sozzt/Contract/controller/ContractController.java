package pl.jkap.sozzt.Contract.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.Contract.controller.dto.ContractDTO;
import pl.jkap.sozzt.Contract.model.Contract;
import pl.jkap.sozzt.Contract.service.ContractService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/contracts")
    public List<ContractDTO> getContracts(@RequestParam(required = false) int page) {
        int pageNumber = page >= 0 ? page : 0;
        return ContractMapper.mapToContractDtos(contractService.getContracts(pageNumber));
    }

    @GetMapping("/contracts/files")
    public List<Contract> getContractsWithFiles(@RequestParam(required = false) int page) {
        int pageNumber = page >= 0 ? page : 0;
        return contractService.getContractsWithFiles(pageNumber);
    }


    @GetMapping("/contract/{id}")
    public Contract getSingleContract(@PathVariable long id) {
        return contractService.getContract(id);
    }

    @PostMapping("/contract")
    public Contract addContract(@RequestBody Contract contracts) {
        return contractService.addContract(contracts);
    }

    @PutMapping("/contract")
    public Contract editContract(@RequestBody Contract contracts) {
        return contractService.editContract(contracts);
    }

    @DeleteMapping("/contract/{id}")
    public void deleteContract(@PathVariable long id) {
        contractService.deleteContract(id);

    }

}
