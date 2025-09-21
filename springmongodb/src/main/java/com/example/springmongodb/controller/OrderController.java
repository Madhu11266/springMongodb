package com.example.springmongodb.controller;

import com.example.springmongodb.entity.Order;
import com.example.springmongodb.repo.OrderRepo;
import com.example.springmongodb.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepo orderRepo;

    public OrderController(OrderService orderService,OrderRepo orderRepo){
        this.orderRepo=orderRepo;
        this.orderService=orderService;
    }

    @GetMapping("/{orderId}")
    public Order findByOrderId(@PathVariable  String orderId){
        return orderRepo.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
    }

    @GetMapping
    public List<Order> findOrders(){
        return orderRepo.findAll();
    }

    @PostMapping
    public Order placeOrder(@RequestParam String userId,@RequestBody List<String> products){

        return orderService.placeOrder(userId,products);
    }
}
