package pl.jkap.sozzt.contract.old.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.contract.domain.Contract;
//import pl.jkap.sozzt.contract.old.service.ContractService;
//
//import java.util.List;
//
//
//@RestController
//@RequiredArgsConstructor
//public class ContractController {
//
//    private final ContractService contractService;
//
//
//    //@PreAuthorize("hasRole('USER')")
////    @GetMapping("/contracts")
////    public List<ContractDTO> getContracts(@RequestParam(required = false) int page) {
////        int pageNumber = page >= 0 ? page : 0;
////        return ContractMapper.mapToContractDtos(contractService.getContracts(pageNumber));
////    }
//
//    @GetMapping("/contracts/files")
//    public List<Contract> getContractsWithFiles(@RequestParam(required = false) int page) {
//        int pageNumber = page >= 0 ? page : 0;
//        return contractService.getContractsWithFiles(pageNumber);
//    }
//
//    @GetMapping("/contracts/{id}")
//    public Contract getSingleContract(@PathVariable long id) {
//        return contractService.getContract(id);
//    }
//
//    @PostMapping("/contracts")
//    public Contract addContract(@RequestBody Contract contracts) {
//        return contractService.addContract(contracts);
//    }
//
//    @PutMapping("/contracts")
//    public Contract editContract(@RequestBody Contract contracts) {
//        return contractService.editContract(contracts);
//    }
//
//    @PutMapping("/contracts/{id}/validateStepContract")
//    public ResponseEntity<Object> validateStepContract(@PathVariable long id) {
//        Contract editedContract = contractService.validateStepContract(id);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(editedContract);
//    }
//
//    @DeleteMapping("/contracts/{id}")
//    public void deleteContract(@PathVariable long id) {
//        contractService.deleteContract(id);
//    }
//
//}
