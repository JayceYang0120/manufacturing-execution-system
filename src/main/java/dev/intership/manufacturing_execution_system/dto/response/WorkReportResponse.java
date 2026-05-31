package dev.intership.manufacturing_execution_system.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkReportResponse {
    
    private Long id;
    private Long dispatchId;

    private Integer completedQuantity;
    private Integer defectiveQuantity;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Integer workingMinutes;

    private String status;
    private String statusDescription;

    private String note;
    private String reportedBy;
}
