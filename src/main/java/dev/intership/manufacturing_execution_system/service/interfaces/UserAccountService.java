package dev.intership.manufacturing_execution_system.service.interfaces;

import java.util.List;
import java.util.UUID;

import dev.intership.manufacturing_execution_system.dto.request.CreateUserAccountRequest;
import dev.intership.manufacturing_execution_system.dto.response.UserAccountResponse;

public interface UserAccountService {
    
    UserAccountResponse createUserAccount(CreateUserAccountRequest request);
    UserAccountResponse getUserAccountById(UUID id);
    List<UserAccountResponse> getOperators();
    List<UserAccountResponse> getAllUserAccounts();
    
}
