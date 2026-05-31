package dev.intership.manufacturing_execution_system.service.interfaces;

import java.util.List;

import dev.intership.manufacturing_execution_system.dto.request.CreateProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.request.QueryProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.response.OrderProgressResponse;
import dev.intership.manufacturing_execution_system.dto.response.ProductionOrderResponse;

public interface ProductionOrderService {
    
    ProductionOrderResponse createOrder(CreateProductionOrderRequest request);

    ProductionOrderResponse getOrderById(Long orderId);

    List<ProductionOrderResponse> getOrders(QueryProductionOrderRequest request);

    ProductionOrderResponse updateOrder(Long orderId, UpdateProductionOrderRequest request);

    void deleteOrder(Long orderId);

    OrderProgressResponse calculateProgress(Long orderId);

    void updateOrderStatus(Long orderId);
}
