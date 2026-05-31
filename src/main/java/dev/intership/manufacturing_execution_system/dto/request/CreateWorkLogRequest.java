package dev.intership.manufacturing_execution_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkLogRequest {
    
    @NotNull
    private String logType;

    private String note;
}
