package dev.intership.manufacturing_execution_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.intership.manufacturing_execution_system.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Boolean existsByTaxId(String taxId);    
}
