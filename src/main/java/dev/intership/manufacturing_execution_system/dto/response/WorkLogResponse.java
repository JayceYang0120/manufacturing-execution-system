package dev.intership.manufacturing_execution_system.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkLogResponse {
    
    private Long id;
    private Long dispatchId;
    private String logType;
    private String logTypeDescription;
    private String note;
    private LocalDateTime logTime;
    private String createdBy;
}
