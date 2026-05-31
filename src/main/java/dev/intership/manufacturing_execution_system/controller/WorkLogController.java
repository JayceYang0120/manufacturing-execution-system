package dev.intership.manufacturing_execution_system.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkLogRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkLogResponse;
import dev.intership.manufacturing_execution_system.service.interfaces.WorkLogService;
import jakarta.validation.Valid;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/dispatches/{dispatchId}/logs")
public class WorkLogController {
    
    @Autowired
    private WorkLogService workLogService;

    @PostMapping("/start")
    public ResponseEntity<WorkLogResponse> startWorkLog(@PathVariable Long dispatchId, @RequestParam(required = false) String note) {
        WorkLogResponse response = workLogService.startWork(dispatchId, note);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/pause")
    public ResponseEntity<WorkLogResponse> pauseWorkLog(@PathVariable Long dispatchId, @RequestParam(required = false) String note) {
        WorkLogResponse response = workLogService.pauseWork(dispatchId, note);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/resume")
    public ResponseEntity<WorkLogResponse> resumeWorkLog(@PathVariable Long dispatchId, @RequestParam(required = false) String note) {
        WorkLogResponse response = workLogService.resumeWork(dispatchId, note);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/complete")
    public ResponseEntity<WorkLogResponse> completeWorkLog(@PathVariable Long dispatchId, @RequestParam(required = false) String note) {
        WorkLogResponse response = workLogService.completeWork(dispatchId, note);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/event")
    public ResponseEntity<WorkLogResponse> logEvent(@PathVariable Long dispatchId, @Valid @RequestBody CreateWorkLogRequest request) {
        WorkLogResponse response = workLogService.logEvent(dispatchId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkLogResponse>> getLogsBetween(@PathVariable Long dispatchId, @RequestParam String start, @RequestParam String end) {
        List<WorkLogResponse> logs = workLogService.getLogsBetween(dispatchId, LocalDateTime.parse(start), LocalDateTime.parse(end));
        return ResponseEntity.ok(logs);
    }
}
