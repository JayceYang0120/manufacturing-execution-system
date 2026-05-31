package dev.intership.manufacturing_execution_system.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignOperatorRequest {

    @NotNull
    private UUID operatorId;
}