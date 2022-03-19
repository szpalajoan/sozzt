package pl.jkap.sozzt.service;


import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import pl.jkap.sozzt.model.Orders;
import pl.jkap.sozzt.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Orders> getOrders(){
        return orderRepository.findAll();
    }

    public Orders getSingleOrders(long id) {
        return orderRepository.findById(id)
                .orElseThrow();
    }

    public Orders addOrder(Orders orders) {
        return orderRepository.save(orders);
    }
}
