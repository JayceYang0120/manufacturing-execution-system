package dev.intership.manufacturing_execution_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.intership.manufacturing_execution_system.dto.request.CreateProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.request.QueryProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.response.OrderProgressResponse;
import dev.intership.manufacturing_execution_system.dto.response.ProductionOrderResponse;
import dev.intership.manufacturing_execution_system.service.interfaces.ProductionOrderService;
import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/orders")
public class ProductionOrderController {
    
    @Autowired
    private ProductionOrderService productionOrderService;

    @GetMapping
    public ResponseEntity<List<ProductionOrderResponse>> getProductionOrders(@ModelAttribute QueryProductionOrderRequest request) {
        List<ProductionOrderResponse> responses = productionOrderService.getOrders(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ProductionOrderResponse> getProductionOrderByIdResponseEntity(@PathVariable Long orderId) {
        ProductionOrderResponse response = productionOrderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping
    public ResponseEntity<ProductionOrderResponse> createProductionOrder(@Valid @RequestBody CreateProductionOrderRequest request) {
        ProductionOrderResponse response = productionOrderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ProductionOrderResponse> updateProductionOrder(@PathVariable Long orderId, @Valid @RequestBody UpdateProductionOrderRequest request) {
        ProductionOrderResponse response = productionOrderService.updateOrder(orderId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteProductionOrder(@PathVariable Long orderId) {
        productionOrderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/progress")
    public ResponseEntity<OrderProgressResponse> getProductionOrderProgress(@PathVariable Long orderId) {
        OrderProgressResponse response = productionOrderService.calculateProgress(orderId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
