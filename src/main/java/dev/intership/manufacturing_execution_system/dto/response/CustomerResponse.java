package dev.intership.manufacturing_execution_system.dto.response;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    
    private UUID id;
    private String name;
    private String taxId;
    private String address;
    private String phone;
}
