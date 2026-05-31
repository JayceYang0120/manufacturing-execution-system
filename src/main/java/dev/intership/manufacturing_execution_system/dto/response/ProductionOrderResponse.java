package dev.intership.manufacturing_execution_system.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductionOrderResponse {
    
    private Long id;
    private String productName;
    private String productDescription;
    private Integer quantity;
    private String status;
    private String statusDescription;
    private Double completionPercentage;
    private LocalDateTime orderDate;
}
