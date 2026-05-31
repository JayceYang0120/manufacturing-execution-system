package dev.intership.manufacturing_execution_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.intership.manufacturing_execution_system.dto.request.CreateUserAccountRequest;
import dev.intership.manufacturing_execution_system.dto.response.UserAccountResponse;
import dev.intership.manufacturing_execution_system.service.interfaces.UserAccountService;
import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserAccountController {
    
    @Autowired
    private UserAccountService userAccountService;
    
    @PostMapping
    public ResponseEntity<UserAccountResponse> createUser(@Valid @RequestBody CreateUserAccountRequest request) {
        UserAccountResponse response = userAccountService.createUserAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserAccountResponse> getUser(@PathVariable UUID userId) {
        UserAccountResponse response = userAccountService.getUserAccountById(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserAccountResponse>> getAllUsers() {
        List<UserAccountResponse> responses = userAccountService.getAllUserAccounts();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responses);
    }

    @GetMapping("/operators")
    public ResponseEntity<List<UserAccountResponse>> getOperators() {
        List<UserAccountResponse> responses = userAccountService.getOperators();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responses);
    }
}
