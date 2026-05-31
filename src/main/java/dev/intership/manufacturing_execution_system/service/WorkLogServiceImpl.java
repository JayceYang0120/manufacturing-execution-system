package dev.intership.manufacturing_execution_system.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.intership.manufacturing_execution_system.dto.request.CreateWorkLogRequest;
import dev.intership.manufacturing_execution_system.dto.response.WorkLogResponse;
import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.entity.WorkDispatch;
import dev.intership.manufacturing_execution_system.entity.WorkLog;
import dev.intership.manufacturing_execution_system.enums.DispatchStatus;
import dev.intership.manufacturing_execution_system.enums.LogType;
import dev.intership.manufacturing_execution_system.enums.RoleType;
import dev.intership.manufacturing_execution_system.repository.UserAccountRepository;
import dev.intership.manufacturing_execution_system.repository.WorkDispatchRepository;
import dev.intership.manufacturing_execution_system.repository.WorkLogRepository;
import dev.intership.manufacturing_execution_system.service.interfaces.WorkLogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkLogServiceImpl implements WorkLogService{
    
    private final WorkLogRepository workLogRepository;
    private final WorkDispatchRepository workDispatchRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public WorkLogResponse startWork(Long dispatchId, String note) {
        return createLog(dispatchId, LogType.START, note, DispatchStatus.IN_PROGRESS);
    }

    @Override
    public WorkLogResponse pauseWork(Long dispatchId, String note) {
        return createLog(dispatchId, LogType.PAUSE, note, DispatchStatus.PAUSED);
    }

    @Override
    public WorkLogResponse resumeWork(Long dispatchId, String note) {
        return createLog(dispatchId, LogType.RESUME, note, DispatchStatus.IN_PROGRESS);
    }

    @Override
    public WorkLogResponse completeWork(Long dispatchId, String note) {
        return createLog(dispatchId, LogType.COMPLETE, note, DispatchStatus.COMPLETED);
    }

    @Override
    public WorkLogResponse logEvent(Long dispatchId, CreateWorkLogRequest request) {
        LogType logType = LogType.from(request.getLogType().toUpperCase());
        return createLog(dispatchId, logType, request.getNote(), null);
    }

    @Override
    public List<WorkLogResponse> getLogsBetween(Long dispatchId, LocalDateTime start, LocalDateTime end) {
        List<WorkLog> logs = workLogRepository.findByDispatchAndTime(dispatchId, start, end);
        return logs.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<WorkLog> getLogEntitiesBetween(Long dispatchId, LocalDateTime start, LocalDateTime end) {
        return workLogRepository.findByDispatchAndTime(dispatchId, start, end);
    }

    private void validateState(DispatchStatus currentStatus, LogType action) {
        switch (action) {
            case START -> {
                if (currentStatus != DispatchStatus.ASSIGNED && currentStatus != DispatchStatus.PAUSED) {
                    throw new RuntimeException("Work dispatch must be in ASSIGNED or PAUSED status to start");
                }
            }
            case PAUSE -> {
                if (currentStatus != DispatchStatus.IN_PROGRESS) {
                    throw new RuntimeException("Work dispatch must be in IN_PROGRESS status to pause");
                }
            }
            case RESUME -> {
                if (currentStatus != DispatchStatus.PAUSED) {
                    throw new RuntimeException("Work dispatch must be in PAUSED status to resume");
                }
            }
            case COMPLETE -> {
                if (currentStatus != DispatchStatus.IN_PROGRESS) {
                    throw new RuntimeException("Work dispatch must be in IN_PROGRESS status to complete");
                }
            }
            default -> {
                // ISSUE / NOTE do not require state validation
            }
        }
    }

    private WorkLogResponse createLog(Long dispatchId, LogType logType, String note, DispatchStatus nextStatus) {
        WorkDispatch dispatch = workDispatchRepository.findById(dispatchId)
                .orElseThrow(() -> new RuntimeException("Work dispatch not found"));

        // UserAccount user = userAccountRepository.findById(SecurityUtil.getCurrentUserId())
        //         .orElseThrow(() -> new RuntimeException("User not found"));
        UserAccount user = userAccountRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("User not found"));

        if (dispatch.getOperatorId() == null) {
            throw new RuntimeException("This work dispatch is not assigned to any operator");
        }
        if  (!dispatch.getOperatorId().equals(user.getId()) && RoleType.OPERATOR.equals(user.getRole())) {
            throw new RuntimeException("You are not assigned to this work dispatch");
        }

        validateState(dispatch.getStatus(), logType);

        WorkLog log = new WorkLog(dispatch, user, logType, note);
        workLogRepository.save(log);

        if (nextStatus != null) {
            dispatch.setStatus(nextStatus);
            workDispatchRepository.save(dispatch);
        }

        return mapToResponse(log);
    }

    private WorkLogResponse mapToResponse(WorkLog log) {
        return new WorkLogResponse(
                log.getId(),
                log.getWorkDispatch().getId(),
                log.getLogType().name(),
                log.getLogType().getDescription(),
                log.getNote(),
                log.getLogTime(),
                log.getCreatedBy().getUsername()
        );
    }
}
