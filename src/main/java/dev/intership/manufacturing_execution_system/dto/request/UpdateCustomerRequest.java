package dev.intership.manufacturing_execution_system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerRequest {
    
    private String name;
    private String taxId;
    private String address;
    private String phone;
}
