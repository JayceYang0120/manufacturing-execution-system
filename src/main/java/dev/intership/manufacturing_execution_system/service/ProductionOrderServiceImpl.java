package dev.intership.manufacturing_execution_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.intership.manufacturing_execution_system.dto.request.CreateProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.request.QueryProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.request.UpdateProductionOrderRequest;
import dev.intership.manufacturing_execution_system.dto.response.OrderProgressResponse;
import dev.intership.manufacturing_execution_system.dto.response.ProductionOrderResponse;
import dev.intership.manufacturing_execution_system.entity.Customer;
import dev.intership.manufacturing_execution_system.entity.ProductionOrder;
import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.enums.Product;
import dev.intership.manufacturing_execution_system.enums.ProductionOrderStatus;
import dev.intership.manufacturing_execution_system.repository.CustomerRepository;
import dev.intership.manufacturing_execution_system.repository.ProductionOrderRepository;
import dev.intership.manufacturing_execution_system.repository.UserAccountRepository;
import dev.intership.manufacturing_execution_system.repository.WorkReportRepository;
import dev.intership.manufacturing_execution_system.service.interfaces.ProductionOrderService;
import dev.intership.manufacturing_execution_system.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final ProductionOrderRepository orderRepository;
    private final WorkReportRepository workReportRepository;
    private final CustomerRepository customerRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public ProductionOrderResponse createOrder(CreateProductionOrderRequest request) {

        Product product = Product.from(request.getProductName());
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ProductionOrder order = new ProductionOrder(
                request.getQuantity(),
                product,
                customer,
                getCurrentUser().getId()
        );

        // order.setCreatedBy(SecurityUtil.getCurrentUserId());

        orderRepository.save(order);

        return mapToResponse(order, 0.0);
    }

    @Override
    public ProductionOrderResponse getOrderById(Long orderId) {

        ProductionOrder order = getOrderOrThrow(orderId);
        OrderProgressResponse progress = calculateProgress(orderId);

        return mapToResponse(order, progress.getPercentage());
    }

    @Override
    public List<ProductionOrderResponse> getOrders(QueryProductionOrderRequest request) {

        List<ProductionOrder> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> {
                    OrderProgressResponse progress = calculateProgress(order.getId());
                    return mapToResponse(order, progress.getPercentage());
                })
                .toList();
    }

    @Override
    public ProductionOrderResponse updateOrder(Long orderId, UpdateProductionOrderRequest request) {

        ProductionOrder order = getOrderOrThrow(orderId);

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (CommonUtil.hasText(request.getProductName())) {
            Product product = Product.from(request.getProductName());
            order.setProductName(product);
        }

        order.setQuantity(request.getQuantity());
        order.setCustomer(customer);

        orderRepository.save(order);

        OrderProgressResponse progress = calculateProgress(orderId);

        return mapToResponse(order, progress.getPercentage());
    }

    @Override
    public void deleteOrder(Long orderId) {

        ProductionOrder order = getOrderOrThrow(orderId);

        if ("IN_PROGRESS".equals(order.getProductionOrderStatus().name())) {
            throw new RuntimeException("Cannot delete order in progress");
        }

        orderRepository.delete(order);
    }

    @Override
    public OrderProgressResponse calculateProgress(Long orderId) {

        ProductionOrder order = getOrderOrThrow(orderId);

        int total = order.getQuantity();

        Integer completed = workReportRepository.sumCompletedQuantityByOrderId(orderId);
        if (completed == null) completed = 0;

        double percentage = total == 0 ? 0 : (double) completed / total * 100;

        OrderProgressResponse res = new OrderProgressResponse(
                orderId,
                total,
                completed,
                percentage
        );

        return res;
    }

    @Override
    public void updateOrderStatus(Long orderId) {

        ProductionOrder order = getOrderOrThrow(orderId);
        double percent = calculateProgress(orderId).getPercentage();

        if (percent == 0) {
            order.setProductionOrderStatus(ProductionOrderStatus.CREATED);
        } else if (percent < 100) {
            order.setProductionOrderStatus(ProductionOrderStatus.IN_PROGRESS);
        } else {
            order.setProductionOrderStatus(ProductionOrderStatus.COMPLETED);
        }

        orderRepository.save(order);
    }

    private ProductionOrder getOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("ProductionOrder not found"));
    }

    private UserAccount getCurrentUser() {

        return userAccountRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No user found"));

        /*
        UUID userId = SecurityUtil.getCurrentUserId();

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        */
    }

    private ProductionOrderResponse mapToResponse(ProductionOrder order, Double percentage) {

        ProductionOrderResponse res = new ProductionOrderResponse(
                order.getId(),
                order.getProductName().name(),
                order.getProductName().getDescription(),
                order.getQuantity(),
                order.getProductionOrderStatus().name(),
                order.getProductionOrderStatus().getDescription(),
                percentage,
                order.getOrderDate()
        );

        return res;
    }
}