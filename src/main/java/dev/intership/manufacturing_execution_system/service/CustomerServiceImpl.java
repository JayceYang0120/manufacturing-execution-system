package dev.intership.manufacturing_execution_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.intership.manufacturing_execution_system.dto.request.CreateCustomerRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateCustomerRequest;
import dev.intership.manufacturing_execution_system.dto.response.CustomerResponse;
import dev.intership.manufacturing_execution_system.entity.Customer;
import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.repository.CustomerRepository;
import dev.intership.manufacturing_execution_system.repository.UserAccountRepository;
import dev.intership.manufacturing_execution_system.service.interfaces.CustomerService;
import dev.intership.manufacturing_execution_system.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserAccountRepository userRepository;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        Customer customer = new Customer(
                request.getName(),
                request.getTaxId(),
                request.getPhone(),
                request.getAddress(),
                getCurrentUser()
        );

        if (customerRepository.existsByTaxId(request.getTaxId())) {
            throw new RuntimeException("taxId already exists");
        }

        customerRepository.save(customer);

        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerById(UUID customerId) {

        Customer customer = getOrThrow(customerId);

        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(
            UUID customerId,
            UpdateCustomerRequest request) {

        Customer customer = getOrThrow(customerId);

        if (hasText(request.getName())) {
            customer.setName(request.getName());
        }

        if (hasText(request.getTaxId())) {
            customer.setTaxId(request.getTaxId());
        }

        if (hasText(request.getAddress())) {
            customer.setAddress(request.getAddress());
        }

        if (hasText(request.getPhone())) {
            customer.setPhone(request.getPhone());
        }

        customerRepository.save(customer);

        return mapToResponse(customer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {

        Customer customer = getOrThrow(customerId);

        if (!customer.getProductionOrders().isEmpty()) {
            throw new RuntimeException("Customer has orders, cannot delete");
        }

        customerRepository.delete(customer);
    }

    private Customer getOrThrow(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    private UserAccount getCurrentUser() {

        UUID userId = SecurityUtil.getCurrentUserId();

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private CustomerResponse mapToResponse(Customer customer) {

        CustomerResponse res = new CustomerResponse();

        res.setId(customer.getId());
        res.setName(customer.getName());
        res.setTaxId(customer.getTaxId());
        res.setAddress(customer.getAddress());
        res.setPhone(customer.getPhone());

        return res;
    }
}