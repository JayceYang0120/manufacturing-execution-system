package dev.intership.manufacturing_execution_system.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.intership.manufacturing_execution_system.enums.LogType;
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
@Table(name = "work_log")
@Getter
@Setter
@NoArgsConstructor
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogType logType;

    @Column(nullable = true)
    private String note;

    @Column(nullable = false)
    private LocalDateTime logTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_dispatch_id", nullable = false)
    private WorkDispatch workDispatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @JsonIgnore
    private UserAccount createdBy;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.logTime = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public WorkLog(
            WorkDispatch workDispatch,
            UserAccount createdBy,
            LogType logType,
            String note) {

        this.workDispatch = workDispatch;
        this.createdBy = createdBy;
        this.logType = logType;
        this.note = note;
    }
}