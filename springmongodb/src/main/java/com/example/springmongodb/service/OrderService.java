package com.example.springmongodb.service;

import com.example.springmongodb.entity.Order;
import com.example.springmongodb.entity.Product;
import com.example.springmongodb.entity.User;
import com.example.springmongodb.repo.OrderRepo;
import com.example.springmongodb.repo.ProductRepo;
import com.example.springmongodb.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo, ProductRepo productRepo, UserRepo userRepo){
        this.orderRepo=orderRepo;
        this.productRepo=productRepo;
        this.userRepo=userRepo;
    }

    @Transactional
    public Order placeOrder(String userId,List<String> products){

        User user=userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        double totalAmount=0;

        for(String str:products){
            Product prod=productRepo.findById(str).orElseThrow(()->new RuntimeException("product not found"));
            totalAmount+=prod.getPrice();
            if(prod.getStock()<0)throw new RuntimeException("Out of sock");
            prod.setStock(prod.getStock()-1);
            productRepo.save(prod);
        }
        Order order=new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(user.getUserId());
        order.setProducts(products);
        order.setTotalAmount(totalAmount);
        return orderRepo.save(order);
    }

}
