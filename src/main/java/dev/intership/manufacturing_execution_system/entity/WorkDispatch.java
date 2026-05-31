package dev.intership.manufacturing_execution_system.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.intership.manufacturing_execution_system.enums.DispatchStatus;
import dev.intership.manufacturing_execution_system.enums.Equipment;
import dev.intership.manufacturing_execution_system.enums.ProcessName;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "work_dispatch")
@Getter
@Setter
@NoArgsConstructor
public class WorkDispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String dispatchNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessName processName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Equipment equipment;

    @Column(nullable = false)
    private Integer dispatchQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DispatchStatus status;

    @Column(nullable = false)
    private LocalDateTime dispatchTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id", nullable = false)
    private ProductionOrder productionOrder;

    @Column(name = "operator_id", nullable = true)
    private UUID operatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = true)
    private UserAccount assignedBy;

    @OneToMany(mappedBy = "workDispatch")
    @JsonIgnore
    private List<WorkLog> workLogs;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.dispatchTime = this.createdAt;
        this.status = DispatchStatus.CREATED;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public WorkDispatch(
            String dispatchNo,
            ProcessName processName,
            Equipment equipment,
            Integer dispatchQuantity,
            ProductionOrder productionOrder,
            UUID operatorId,
            UserAccount assignedBy) {

        this.dispatchNo = dispatchNo;
        this.processName = processName;
        this.equipment = equipment;
        this.dispatchQuantity = dispatchQuantity;
        this.productionOrder = productionOrder;
        this.operatorId = operatorId;
        this.assignedBy = assignedBy;
    }
}