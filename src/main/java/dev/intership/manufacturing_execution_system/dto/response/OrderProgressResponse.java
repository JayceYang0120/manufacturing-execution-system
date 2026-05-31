package dev.intership.manufacturing_execution_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderProgressResponse {
    
    private Long orderId;
    private Integer totalQuantity;
    private Integer completedQuantity;
    private Double percentage;
}
