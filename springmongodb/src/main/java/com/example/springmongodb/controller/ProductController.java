package com.example.springmongodb.controller;

import com.example.springmongodb.entity.Product;
import com.example.springmongodb.entity.User;
import com.example.springmongodb.repo.ProductRepo;
import com.example.springmongodb.repo.UserRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductRepo productRepo;

    public ProductController(ProductRepo productRepo){
        this.productRepo=productRepo;
    }

    @GetMapping
    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable String productId){
        Optional<Product> product=productRepo.findById(productId);
        return product.orElse(null);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        product.setProductId(UUID.randomUUID().toString());
        return productRepo.save(product);
    }


}
