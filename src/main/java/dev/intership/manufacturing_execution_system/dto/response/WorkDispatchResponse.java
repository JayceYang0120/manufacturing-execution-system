package dev.intership.manufacturing_execution_system.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkDispatchResponse {
    
    private Long id;
    private String dispatchNo;
    private String processName;
    private String processDescription;
    private String equipment;
    private String equipmentDescription;
    private Integer dispatchQuantity;
    private Long productionOrderId;
    private UUID operatorId;
    private String operatorName;
    private String assignedByName;
    private String status;
    private String statusDescription;
    private LocalDateTime dispatchTime;
}
