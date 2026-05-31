package dev.intership.manufacturing_execution_system.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAccountResponse {
    private UUID id;
    private String username;
    private String role;
    private Boolean enabled;
}
