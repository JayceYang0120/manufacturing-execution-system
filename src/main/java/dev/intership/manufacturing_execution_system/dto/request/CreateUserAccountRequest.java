package dev.intership.manufacturing_execution_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserAccountRequest {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank  
    private String role;
}
