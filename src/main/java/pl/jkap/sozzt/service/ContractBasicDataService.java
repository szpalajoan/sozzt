package pl.jkap.sozzt.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jkap.sozzt.model.ContractBasicData;
import pl.jkap.sozzt.repository.ContractBasicDataRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractBasicDataService {

    private final ContractBasicDataRepository orderRepository;

    public List<ContractBasicData> getOrders(){
        return orderRepository.findAll();
    }

    public ContractBasicData getSingleOrders(long id) {
        return orderRepository.findById(id)
                .orElseThrow();
    }

    public ContractBasicData addOrder(ContractBasicData orders) {
        return orderRepository.save(orders);
    }

    @Transactional //zeby byla tylko jedna transakcja
    public ContractBasicData editOrder(ContractBasicData order) {
        ContractBasicData orderEdited = orderRepository.findById(order.getId()).orElseThrow();
        orderEdited.setInvoice_number(order.getInvoice_number());
        orderEdited.setExecutive(order.getExecutive());
        orderEdited.setLocation(order.getLocation());
        return orderEdited;
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
