package dev.intership.manufacturing_execution_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.intership.manufacturing_execution_system.entity.ProductionOrder;

public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {
}