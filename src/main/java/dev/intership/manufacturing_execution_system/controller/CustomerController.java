package dev.intership.manufacturing_execution_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.intership.manufacturing_execution_system.dto.request.CreateCustomerRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateCustomerRequest;
import dev.intership.manufacturing_execution_system.dto.response.CustomerResponse;
import dev.intership.manufacturing_execution_system.service.interfaces.CustomerService;
import jakarta.validation.Valid;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID customerId) {
        CustomerResponse customer = customerService.getCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID customerId, @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
