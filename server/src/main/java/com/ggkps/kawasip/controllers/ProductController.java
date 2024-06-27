package com.ggkps.kawasip.controllers;

import com.ggkps.kawasip.entities.Product;
import com.ggkps.kawasip.repositories.ProductRepository;
import com.ggkps.kawasip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ggkps.kawasip.models.ProductModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/product/home")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("Welcome to ProductController");
    }

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody ProductModel productModel){
        Product newProduct = Product.builder()
                .created_at(new Date())
                .name(productModel.getName())
                .details(productModel.getDetails())
                .stocks(productModel.getStocks())
                .build();

        productRepository.save(newProduct);
        return ResponseEntity.ok(newProduct.toString());
    }

    @GetMapping("/product")
    public ResponseEntity<String> products(){
        List <Product> products = productRepository.findAll();
        return ResponseEntity.ok(products.toString());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> ResponseEntity.ok(value.toString())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/product/order/{id}")
    public ResponseEntity<String> getProductByOrderId(@PathVariable Integer id){
        List<Product> products = productRepository.findByOrderId(id);
        return ResponseEntity.ok(products.toString());
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestBody ProductModel productModel){
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(value -> {
            value.setName(productModel.getName());
            value.setDetails(productModel.getDetails());
            value.setStocks(productModel.getStocks());
            productRepository.save(value);
        });

        return product.map(value -> ResponseEntity.ok(value.toString())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(value -> productRepository.deleteById(value.getId()));
        return product.map(value -> ResponseEntity.ok("Product deleted!")).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
