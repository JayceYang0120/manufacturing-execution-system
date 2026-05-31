package dev.intership.manufacturing_execution_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.intership.manufacturing_execution_system.entity.WorkDispatch;

public interface WorkDispatchRepository extends JpaRepository<WorkDispatch, Long> {
    
}
