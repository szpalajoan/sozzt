package pl.jkap.sozzt.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
