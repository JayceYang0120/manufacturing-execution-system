package dev.intership.manufacturing_execution_system.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.intership.manufacturing_execution_system.enums.ReportStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "work_report")
@Getter
@Setter
@NoArgsConstructor
public class WorkReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_dispatch_id", nullable = false)
    private WorkDispatch workDispatch;

    @Column(nullable = false)
    private Integer completedQuantity;

    @Column(nullable = false)
    private Integer defectiveQuantity;

    @Column(nullable = true)
    private LocalDateTime startTime;

    @Column(nullable = true)
    private LocalDateTime endTime;

    private Integer workingMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus reportStatus;

    @Column(nullable = true)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_by", nullable = false)
    @JsonIgnore
    private UserAccount reportedBy;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.startTime = this.createdAt;
        this.endTime = null;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public WorkReport(
            WorkDispatch workDispatch,
            Integer completedQuantity,
            Integer defectiveQuantity,
            ReportStatus reportStatus,
            String note,
            UserAccount reportedBy) {

        this.workDispatch = workDispatch;
        this.completedQuantity = completedQuantity;
        this.defectiveQuantity = defectiveQuantity;
        this.reportStatus = reportStatus;
        this.note = note;
        this.reportedBy = reportedBy;
    }
}