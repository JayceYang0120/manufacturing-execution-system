package dev.intership.manufacturing_execution_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.intership.manufacturing_execution_system.dto.request.CreateUserAccountRequest;
import dev.intership.manufacturing_execution_system.dto.response.UserAccountResponse;
import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.enums.RoleType;
import dev.intership.manufacturing_execution_system.repository.UserAccountRepository;
import dev.intership.manufacturing_execution_system.service.interfaces.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
    
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccountResponse createUserAccount(CreateUserAccountRequest request) {
        UserAccount userAccount = new UserAccount(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            RoleType.valueOf(request.getRole().toUpperCase())
        );
        userAccountRepository.save(userAccount);
        return mapToResponse(userAccount);
    }

    @Override
    public UserAccountResponse getUserAccountById(UUID id) {
        UserAccount userAccount = userAccountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User account not found"));
        return mapToResponse(userAccount);
    }

    @Override
    public List<UserAccountResponse> getOperators() {
        return userAccountRepository.findByRole(RoleType.OPERATOR).stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public List<UserAccountResponse> getAllUserAccounts() {
        return userAccountRepository.findAll().stream()
            .map(this::mapToResponse)
            .toList();
    }

    private UserAccountResponse mapToResponse(UserAccount userAccount) {
        return new UserAccountResponse(
            userAccount.getId(),
            userAccount.getUsername(),
            userAccount.getRole().name(),
            userAccount.getEnabled()
        );
    }
}
