package dev.intership.manufacturing_execution_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerRequest {
    
    @NotBlank
    private String name;

    @NotBlank
    private String taxId;

    private String address;

    @NotBlank
    private String phone;
}
