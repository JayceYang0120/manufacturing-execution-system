package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductionOrderStatus {
    
    CREATED("已創建"),
    IN_PROGRESS("製造中"),
    COMPLETED("已完成");

    private final String description;

    public static ProductionOrderStatus from(String value) {
        try {
            return ProductionOrderStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid production order status: " + value);
        }
    }
}
