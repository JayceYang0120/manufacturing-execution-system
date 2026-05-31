package dev.intership.manufacturing_execution_system.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkReportRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateWorkReportRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkLogResponse;
import dev.intership.manufacturing_execution_system.dto.response.WorkReportResponse;
import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.entity.WorkDispatch;
import dev.intership.manufacturing_execution_system.entity.WorkLog;
import dev.intership.manufacturing_execution_system.entity.WorkReport;
import dev.intership.manufacturing_execution_system.enums.ReportStatus;
import dev.intership.manufacturing_execution_system.repository.UserAccountRepository;
import dev.intership.manufacturing_execution_system.repository.WorkDispatchRepository;
import dev.intership.manufacturing_execution_system.repository.WorkReportRepository;
import dev.intership.manufacturing_execution_system.service.interfaces.WorkLogService;
import dev.intership.manufacturing_execution_system.service.interfaces.WorkReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkReportServiceImpl implements WorkReportService {
    
    private final WorkReportRepository workReportRepository;
    private final WorkDispatchRepository workDispatchRepository;
    private final UserAccountRepository userAccountRepository;
    private final WorkLogService workLogService;

    @Override
    public WorkReportResponse createWorkReport(CreateWorkReportRequest request) {
        WorkDispatch dispatch = workDispatchRepository.findById(request.getDispatchId())
                .orElseThrow(() -> new RuntimeException("Work dispatch not found with id: " + request.getDispatchId()));
        
        // UserAccount user = userAccountRepository.findById(SecurityUtil.getCurrentUserId())
                // .orElseThrow(() -> new RuntimeException("User not found with id: " + SecurityUtil.getCurrentUserId()));
        UserAccount user = userAccountRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("User not found"));

        WorkReport report = new WorkReport(
                dispatch,
                request.getCompletedQuantity(),
                request.getDefectiveQuantity(),
                ReportStatus.DRAFT,
                request.getNote(),
                user
        );
        workReportRepository.save(report);
        return mapToResponse(report);
    }

    @Override
    public WorkReportResponse updateWorkReport(Long reportId, UpdateWorkReportRequest request) {
        WorkReport report = getOrThrow(reportId);

        report.setCompletedQuantity(request.getCompletedQuantity());
        report.setDefectiveQuantity(request.getDefectiveQuantity());
        report.setNote(request.getNote());

        workReportRepository.save(report);

        return mapToResponse(report);
    }

    @Override
    public WorkReportResponse submitWorkReport(Long reportId) {
        WorkReport report = getOrThrow(reportId);

        if (report.getReportStatus() != ReportStatus.DRAFT) {
            throw new RuntimeException("Only draft reports can be submitted.");
        }

        report.setReportStatus(ReportStatus.SUBMITTED);
        report.setEndTime(LocalDateTime.now());
        calculateWorkingTime(report);

        workReportRepository.save(report);

        return mapToResponse(report);
    }

    @Override
    public WorkReportResponse cancelWorkReport(Long reportId) {
        WorkReport report = getOrThrow(reportId);

        if (report.getReportStatus() == ReportStatus.CANCELLED) {
            throw new RuntimeException("Report is already canceled.");
        }

        report.setReportStatus(ReportStatus.CANCELLED);
        workReportRepository.save(report);

        return mapToResponse(report);
    }

    @Override
    public WorkReportResponse getWorkReportById(Long reportId) {
        WorkReport report = getOrThrow(reportId);
        return mapToResponse(report);
    }

    @Override
    public List<WorkReportResponse> getReportsByDispatchId(Long dispatchId) {
        List<WorkReportResponse> reports = workReportRepository.findAll().stream()
                .filter(r -> r.getWorkDispatch().getId().equals(dispatchId))
                .map(this::mapToResponse)
                .toList();
        return reports;
    }

    @Override
    public List<WorkLogResponse> getLogsByReportId(Long reportId) {
        WorkReport report = getOrThrow(reportId);
        return workLogService.getLogsBetween(report.getWorkDispatch().getId(), report.getStartTime(), report.getEndTime());
    }

    private WorkReport getOrThrow(Long id) {
        return workReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work report not found with id: " + id));
    }

    private void calculateWorkingTime(WorkReport report) {
    
        List<WorkLog> logs = workLogService.getLogEntitiesBetween(report.getWorkDispatch().getId(), report.getStartTime(), LocalDateTime.now());

        int totalMinutes = 0;

        LocalDateTime lastStart = null;

        for (WorkLog log : logs) {
            if (Boolean.TRUE.equals(log.getLogType().isWorking())) {
                lastStart = log.getLogTime();
            }

            if (Boolean.FALSE.equals(log.getLogType().isWorking()) && lastStart != null) {
                totalMinutes += Duration.between(lastStart, log.getLogTime()).toMinutes();
                lastStart = null;
            }
        }

        report.setWorkingMinutes(totalMinutes);
    }

    private WorkReportResponse mapToResponse(WorkReport report) {
        return new WorkReportResponse(
                report.getId(),
                report.getWorkDispatch().getId(),
                report.getCompletedQuantity(),
                report.getDefectiveQuantity(),
                report.getStartTime(),
                report.getEndTime(),
                report.getWorkingMinutes(),
                report.getReportStatus().name(),
                report.getReportStatus().getDescription(),
                report.getNote(),
                report.getReportedBy().getUsername()
        );
    }
}
