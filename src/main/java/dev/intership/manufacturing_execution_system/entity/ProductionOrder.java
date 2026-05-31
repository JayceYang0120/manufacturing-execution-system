package dev.intership.manufacturing_execution_system.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.intership.manufacturing_execution_system.enums.Product;
import dev.intership.manufacturing_execution_system.enums.ProductionOrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "production_order")
@Getter
@Setter
@NoArgsConstructor
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Product productName;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductionOrderStatus productionOrderStatus;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @OneToMany(
        mappedBy = "productionOrder",
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    @JsonIgnore
    private List<WorkDispatch> workDispatchs;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.orderDate = this.createdAt;
        this.productionOrderStatus = ProductionOrderStatus.CREATED;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public ProductionOrder(
            Integer quantity,
            Product productName,
            Customer customer,
            UUID createdBy) {

        super();
        this.quantity = quantity;
        this.productName = productName;
        this.customer = customer;
        this.createdBy = createdBy;
    }
}