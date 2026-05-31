package dev.intership.manufacturing_execution_system.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductionOrderRequest {
    
    @NotBlank
    private String productName;

    @NotBlank
    @Min(1)
    private Integer quantity;

    @NotBlank
    private UUID customerId;
}
