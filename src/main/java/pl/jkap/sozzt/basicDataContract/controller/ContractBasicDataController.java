package pl.jkap.sozzt.basicDataContract.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.basicDataContract.model.ContractBasicData;
import pl.jkap.sozzt.basicDataContract.service.ContractBasicDataService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ContractBasicDataController {


    private final ContractBasicDataService contractBasicDataService;


    @GetMapping("/contracts")
    public List<ContractBasicData> getContracts() {
        return contractBasicDataService.getContractsBasicDataRepository();
    }

    @GetMapping("/contract/{id}")
    public ContractBasicData getSingleContractBasicData(@PathVariable long id) {
        return contractBasicDataService.getContractBasicData(id);
    }

    @PostMapping("/contract")
    public ContractBasicData addContractBasicData(@RequestBody ContractBasicData contractsBasicData) {
        return contractBasicDataService.addContractBasicData(contractsBasicData);
    }

    @PutMapping("/contract")
    public ContractBasicData editContractBasicData(@RequestBody ContractBasicData contractsBasicData) {
        return contractBasicDataService.editContractBasicData(contractsBasicData);
    }

    @DeleteMapping("/contract/{id}")
    public void deleteContractBasicData(@PathVariable long id) {
        contractBasicDataService.deleteContractBasicData(id);

    }

}
