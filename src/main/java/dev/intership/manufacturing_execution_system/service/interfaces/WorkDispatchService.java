package dev.intership.manufacturing_execution_system.service.interfaces;

import java.util.List;
import java.util.UUID;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkDispatchRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkDispatchResponse;
import dev.intership.manufacturing_execution_system.enums.DispatchStatus;

public interface WorkDispatchService {
    
    WorkDispatchResponse createWorkDispatch(CreateWorkDispatchRequest request);
    WorkDispatchResponse getWorkDispatchById(Long id);
    List<WorkDispatchResponse> getAllWorkDispatches();
    WorkDispatchResponse assignOperator(Long dispatchId, UUID operatorId);
    WorkDispatchResponse updateWorkDispatchStatus(Long dispatchId, DispatchStatus status);
}
