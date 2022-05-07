package pl.jkap.sozzt.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.model.ContractBasicData;
import pl.jkap.sozzt.service.ContractBasicDataService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ContractBasicDataController {


    private final ContractBasicDataService contractBasicDataService;


    @GetMapping("/contracts")
    public List<ContractBasicData> getOrders() {
        return contractBasicDataService.getOrders();
    }

    @GetMapping("/contract/{id}")
    public ContractBasicData getSingleOrder(@PathVariable long id) {
        return contractBasicDataService.getSingleOrders(id);
    }

    @PostMapping("/contract")
    public ContractBasicData addOrder(@RequestBody ContractBasicData orders) {
        return contractBasicDataService.addOrder(orders);
    }

    @PutMapping("/contract")
    public ContractBasicData editOrder(@RequestBody ContractBasicData orders) {
        return contractBasicDataService.editOrder(orders);
    }

    @DeleteMapping("/contract/{id}")
    public void deleteOrder(@PathVariable long id) {
        contractBasicDataService.deleteOrder(id);

    }

}
