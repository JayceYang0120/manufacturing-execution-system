package dev.intership.manufacturing_execution_system.service.interfaces;

import java.util.List;
import java.util.UUID;

import dev.intership.manufacturing_execution_system.dto.request.CreateCustomerRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateCustomerRequest;
import dev.intership.manufacturing_execution_system.dto.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse getCustomerById(UUID customerId);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse updateCustomer(UUID customerId, UpdateCustomerRequest request);

    void deleteCustomer(UUID customerId);
}
