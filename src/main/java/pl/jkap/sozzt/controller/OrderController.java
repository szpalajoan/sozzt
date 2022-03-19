package pl.jkap.sozzt.controller;


import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.model.Orders;
import pl.jkap.sozzt.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {


    private final OrderService  orderService;



    @GetMapping("/orders")
   public List<Orders> getOrders(){
       return orderService.getOrders();
   }

    @GetMapping("/orders/{id}")
    public Orders getSingleOrder(@PathVariable long id){
        return orderService.getSingleOrders(id);
    }

    @PostMapping("/orders")
    public Orders addOrder(@RequestBody Orders orders){
        return orderService.addOrder(orders);
    }

}
