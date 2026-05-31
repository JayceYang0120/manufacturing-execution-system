package dev.intership.manufacturing_execution_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkDispatchRequest {
    
    @NotNull
    private Long productionOrderId;

    @NotNull
    private String processName;

    @NotNull
    private String equipment;

    @NotNull
    private Integer dispatchQuantity;
}
