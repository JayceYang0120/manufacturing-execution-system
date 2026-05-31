package dev.intership.manufacturing_execution_system.service.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkLogRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkLogResponse;
import dev.intership.manufacturing_execution_system.entity.WorkLog;

public interface WorkLogService {
    
    WorkLogResponse startWork(Long dispatchId, String note);
    WorkLogResponse pauseWork(Long dispatchId, String note);
    WorkLogResponse resumeWork(Long dispatchId, String note);
    WorkLogResponse completeWork(Long dispatchId, String note);
    WorkLogResponse logEvent(Long dispatchId, CreateWorkLogRequest request);
    List<WorkLogResponse> getLogsBetween(Long dispatchId, LocalDateTime start, LocalDateTime end);
    List<WorkLog> getLogEntitiesBetween(Long dispatchId, LocalDateTime start, LocalDateTime end);
}
