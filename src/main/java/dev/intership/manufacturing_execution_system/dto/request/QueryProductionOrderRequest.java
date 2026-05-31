package dev.intership.manufacturing_execution_system.dto.request;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryProductionOrderRequest {
    
    private String status;
    
    private UUID customerId;
    
    private String productName;
}
