package dev.intership.manufacturing_execution_system.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkReportRequest {
    
    @NotNull
    private Long dispatchId;

    @Min(0)
    private Integer completedQuantity;

    @Min(0)
    private Integer defectiveQuantity;

    private String note;
}
