package dev.intership.manufacturing_execution_system.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductionOrderRequest {

    private String productName;
    
    @Min(1)
    private Integer quantity;

    private UUID customerId;
}
