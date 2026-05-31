package dev.intership.manufacturing_execution_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.intership.manufacturing_execution_system.entity.WorkReport;

public interface WorkReportRepository extends JpaRepository<WorkReport, Long> {

    @Query("""
        SELECT COALESCE(SUM(w.completedQuantity), 0)
        FROM WorkReport w
        WHERE w.workDispatch.productionOrder.id = :orderId
    """)
    Integer sumCompletedQuantityByOrderId(Long orderId);

}