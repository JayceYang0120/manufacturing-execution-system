package dev.intership.manufacturing_execution_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkReportRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateWorkReportRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkLogResponse;
import dev.intership.manufacturing_execution_system.dto.response.WorkReportResponse;
import dev.intership.manufacturing_execution_system.service.interfaces.WorkReportService;
import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/reports")
public class WorkReportController {
    
    @Autowired
    private WorkReportService workReportService;
    
    @GetMapping("/dispatches/{dispatchId}")
    public ResponseEntity<List<WorkReportResponse>> getReportsByDispatch(@PathVariable Long dispatchId) {
        List<WorkReportResponse> report = workReportService.getReportsByDispatchId(dispatchId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(report);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<WorkReportResponse> getReportById(@PathVariable Long reportId) {
        WorkReportResponse report = workReportService.getWorkReportById(reportId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(report);
    }

    @PostMapping
    public ResponseEntity<WorkReportResponse> createWorkReport(@Valid @RequestBody CreateWorkReportRequest request) {
        WorkReportResponse response = workReportService.createWorkReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<WorkReportResponse> updateWorkReport(@PathVariable Long reportId, @Valid @RequestBody UpdateWorkReportRequest request) {
        WorkReportResponse response = workReportService.updateWorkReport(reportId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{reportId}/submit")
    public ResponseEntity<WorkReportResponse> submitWorkReport(@PathVariable Long reportId) {
        WorkReportResponse response = workReportService.submitWorkReport(reportId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{reportId}/cancel")
    public ResponseEntity<WorkReportResponse> cancelWorkReport(@PathVariable Long reportId) {
        WorkReportResponse response = workReportService.cancelWorkReport(reportId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{reportId}/logs")
    public ResponseEntity<List<WorkLogResponse>> getLogsByReportId(@PathVariable Long reportId) {
        List<WorkLogResponse> logs = workReportService.getLogsByReportId(reportId);
        return ResponseEntity.status(HttpStatus.OK).body(logs);
    }
}
