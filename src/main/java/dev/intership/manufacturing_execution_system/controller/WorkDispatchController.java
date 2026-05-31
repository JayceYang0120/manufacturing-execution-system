package dev.intership.manufacturing_execution_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.intership.manufacturing_execution_system.dto.request.AssignOperatorRequest;
import dev.intership.manufacturing_execution_system.dto.request.CreateWorkDispatchRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkDispatchResponse;
import dev.intership.manufacturing_execution_system.enums.DispatchStatus;
import dev.intership.manufacturing_execution_system.service.interfaces.WorkDispatchService;
import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/dispatches")
public class WorkDispatchController {
    
    @Autowired
    private WorkDispatchService workDispatchService;

    @GetMapping("/{dispatchId}")
    public ResponseEntity<WorkDispatchResponse> getWorkDispatchById(@PathVariable Long dispatchId) {
        WorkDispatchResponse response = workDispatchService.getWorkDispatchById(dispatchId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping
    public ResponseEntity<WorkDispatchResponse> createWorkDispatch(@Valid @RequestBody CreateWorkDispatchRequest request) {
        WorkDispatchResponse response = workDispatchService.createWorkDispatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{dispatchId}/assign")
    public ResponseEntity<WorkDispatchResponse> assignOperator(@PathVariable Long dispatchId, @Valid @RequestBody AssignOperatorRequest request) {
        WorkDispatchResponse response = workDispatchService.assignOperator(dispatchId, request.getOperatorId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PatchMapping("/{dispatchId}/status")
    public ResponseEntity<WorkDispatchResponse> updateWorkDispatchStatus(@PathVariable Long dispatchId, @RequestParam String status) {
        WorkDispatchResponse response = workDispatchService.updateWorkDispatchStatus(dispatchId, DispatchStatus.valueOf(status));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
