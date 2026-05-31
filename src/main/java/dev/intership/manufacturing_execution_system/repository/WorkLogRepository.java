package dev.intership.manufacturing_execution_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.intership.manufacturing_execution_system.entity.WorkLog;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    @Query("""
        SELECT w FROM WorkLog w
        WHERE w.workDispatch.id = :dispatchId
        AND w.logTime BETWEEN :start AND :end
    """)
    List<WorkLog> findByDispatchAndTime(
            Long dispatchId,
            LocalDateTime start,
            LocalDateTime end
    );

}