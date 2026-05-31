package dev.intership.manufacturing_execution_system.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String taxId;

    @Column(nullable = true)
    private String address;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "customer",
               cascade = CascadeType.PERSIST,
               orphanRemoval = true)
    @JsonIgnore
    private List<ProductionOrder> productionOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
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
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Customer(String name,
                    String taxId,
                    String phone,
                    String address,
                    UserAccount createdBy) {
        super();
        this.name = name;
        this.taxId = taxId;
        this.phone = phone;
        this.address = address;
        this.createdBy = createdBy;
    }
}