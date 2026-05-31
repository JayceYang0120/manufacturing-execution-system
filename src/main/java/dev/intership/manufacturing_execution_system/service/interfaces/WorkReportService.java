package dev.intership.manufacturing_execution_system.service.interfaces;

import java.util.List;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkReportRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateWorkReportRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkLogResponse;
import dev.intership.manufacturing_execution_system.dto.response.WorkReportResponse;

public interface WorkReportService {
    
    WorkReportResponse createWorkReport(CreateWorkReportRequest request);
    WorkReportResponse updateWorkReport(Long id, UpdateWorkReportRequest request);
    WorkReportResponse submitWorkReport(Long id);
    WorkReportResponse cancelWorkReport(Long id);
    WorkReportResponse getWorkReportById(Long id);
    List<WorkReportResponse> getReportsByDispatchId(Long dispatchId);
    List<WorkLogResponse> getLogsByReportId(Long reportId);
    
}
