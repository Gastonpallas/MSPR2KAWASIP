package com.ggkps.kawasip.controllers;

import com.ggkps.kawasip.entities.Link;
import com.ggkps.kawasip.entities.Order;
import com.ggkps.kawasip.entities.Product;
import com.ggkps.kawasip.models.OrderModel;
import com.ggkps.kawasip.models.ProductModel;
import com.ggkps.kawasip.repositories.LinkRepository;
import com.ggkps.kawasip.repositories.OrderRepository;
import com.ggkps.kawasip.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final LinkRepository linkRepository;
    private final ProductRepository productRepository;

    /**
     * Index
     * @return ResponseEntity<String>
     */
    @GetMapping("/order/home")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("Welcome to OrderController");
    }

    /**
     * Retrieve all orders
     * @return ResponseEntity<String>
     */
    @GetMapping("/order")
    public ResponseEntity<List<Order>> orders(){
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody OrderModel orderModel){
        Order newOrder = Order.builder()
                .customerId(orderModel.getCustomerId())
                .build();

        orderRepository.save(newOrder);
        return ResponseEntity.ok(newOrder.toString());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Integer id){
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Link> links = linkRepository.findByOrderId(order.get().getId());
        List<Product> products = links.stream().map(link -> {
            Optional<Product> product = productRepository.findById(link.getProductId());
            return product.orElse(null);
        }).toList();

        return ResponseEntity.ok(Order.builder()
                .id(order.get().getId())
                .customerId(order.get().getCustomerId())
                .products(products)
                .build().toString());
    }

    @GetMapping("/order/customer/{id}")
    public ResponseEntity<String> getOrderByCustomerId(@PathVariable Integer id){
        List<Order> orders = orderRepository.findByCustomerId(id);
        return ResponseEntity.ok(orders.toString());
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Integer id){
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(value -> orderRepository.deleteById(value.getId()));
        return order.map(value -> ResponseEntity.ok("Order deleted!")).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
