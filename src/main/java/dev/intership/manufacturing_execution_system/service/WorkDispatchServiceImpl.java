package dev.intership.manufacturing_execution_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkDispatchRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkDispatchResponse;
import dev.intership.manufacturing_execution_system.entity.ProductionOrder;
import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.entity.WorkDispatch;
import dev.intership.manufacturing_execution_system.enums.DispatchStatus;
import dev.intership.manufacturing_execution_system.enums.Equipment;
import dev.intership.manufacturing_execution_system.enums.ProcessName;
import dev.intership.manufacturing_execution_system.repository.ProductionOrderRepository;
import dev.intership.manufacturing_execution_system.repository.UserAccountRepository;
import dev.intership.manufacturing_execution_system.repository.WorkDispatchRepository;
import dev.intership.manufacturing_execution_system.service.interfaces.WorkDispatchService;
import dev.intership.manufacturing_execution_system.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkDispatchServiceImpl implements WorkDispatchService {

    private final WorkDispatchRepository workDispatchRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public WorkDispatchResponse createWorkDispatch(CreateWorkDispatchRequest request) {
        ProductionOrder productionOrder = productionOrderRepository.findById(request.getProductionOrderId())
                .orElseThrow(() -> new RuntimeException("Production order not found with id: " + request.getProductionOrderId()));
        
        WorkDispatch dispatch = new WorkDispatch(
            generateDispatchNo(),
            ProcessName.from(request.getProcessName()),
            Equipment.from(request.getEquipment()),
            request.getDispatchQuantity(),
            productionOrder,
            null,
            null
        );
        workDispatchRepository.save(dispatch);
        return mapToResponse(dispatch);
    }
    
    @Override
    public WorkDispatchResponse getWorkDispatchById(Long id) {
        WorkDispatch dispatch = getOrThrow(id);
        return mapToResponse(dispatch);
    }

    @Override
    public List<WorkDispatchResponse> getAllWorkDispatches() {
        return workDispatchRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public WorkDispatchResponse assignOperator(Long dispatchId, UUID operatorId) {
        WorkDispatch dispatch = getOrThrow(dispatchId);
        UserAccount operator = userAccountRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found with id: " + operatorId));
        UserAccount manager = getCurrentUser();
        dispatch.setOperatorId(operator.getId());
        dispatch.setAssignedBy(manager);
        dispatch.setStatus(DispatchStatus.ASSIGNED);
        workDispatchRepository.save(dispatch);
        return mapToResponse(dispatch);
    }

    @Override
    public WorkDispatchResponse updateWorkDispatchStatus(Long dispatchId, DispatchStatus status) {
        WorkDispatch dispatch = getOrThrow(dispatchId);
        dispatch.setStatus(status);
        workDispatchRepository.save(dispatch);
        return mapToResponse(dispatch);
    }

    private WorkDispatch getOrThrow(Long id) {
        return workDispatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work dispatch not found with id: " + id));
    }

    private UserAccount getCurrentUser() {
        return userAccountRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("User not found"));
        // return userAccountRepository.findById(SecurityUtil.getCurrentUserId())
                // .orElseThrow(() -> new RuntimeException("User not found with id: " + SecurityUtil.getCurrentUserId()));
    }

    private WorkDispatchResponse mapToResponse(WorkDispatch dispatch) {
        String operatorName = null;
        String assignedByName = null;
        if (dispatch.getOperatorId() != null) {
            UserAccount operator = userAccountRepository.findById(dispatch.getOperatorId())
                    .orElseThrow(() -> new RuntimeException("Operator not found with id: " + dispatch.getOperatorId()));
            operatorName = operator.getUsername();
        }
        if (dispatch.getAssignedBy() != null) {
            assignedByName = dispatch.getAssignedBy().getUsername();
        }
        return new WorkDispatchResponse(
                dispatch.getId(),
                dispatch.getDispatchNo(),
                dispatch.getProcessName().name(),
                dispatch.getProcessName().getDescription(),
                dispatch.getEquipment().name(),
                dispatch.getEquipment().getDescription(),
                dispatch.getDispatchQuantity(),
                dispatch.getProductionOrder().getId(),
                dispatch.getOperatorId(),
                operatorName,
                assignedByName,
                dispatch.getStatus().name(),
                dispatch.getStatus().getDescription(),
                dispatch.getDispatchTime()
        );
    }

    private String generateDispatchNo() {
        return "D-" + System.currentTimeMillis();
    }
}
