package dev.intership.manufacturing_execution_system.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkReportRequest {
    
    @Min(0)
    private Integer completedQuantity;

    @Min(0)
    private Integer defectiveQuantity;

    private String note;
}
