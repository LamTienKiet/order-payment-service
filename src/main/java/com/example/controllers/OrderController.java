package com.example.controllers;

import com.example.dto.CreateOrderRequest;
import com.example.entities.Order;
import com.example.services.OrderService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ===== 1. GET ALL =====
    @Get
    public HttpResponse<List<Order>> getAllOrders() {
        return HttpResponse.ok(orderService.getAllOrders());
    }

    // ===== 2. GET BY ID =====
    @Get("/{id}")
    public HttpResponse<?> getOrderById(@PathVariable String id) {

        Optional<Order> order = orderService.getOrderById(id);

        return order
                .<HttpResponse<?>>map(HttpResponse::ok)
                .orElseGet(HttpResponse::notFound);
    }

    // ===== 3. CREATE =====
    @Post
    public HttpResponse<Order> createOrder(@Body CreateOrderRequest request) {

        Order order = orderService.createOrder(request);
        return HttpResponse.created(order);
    }

    // ===== 4. UPDATE =====
    @Put("/{id}")
    public HttpResponse<?> updateOrder(@PathVariable String id,
                                       @Body Order updatedOrder) {
        try {
            Order updated = orderService.updateOrder(id, updatedOrder);
            return HttpResponse.ok(updated);
        } catch (Exception e) {
            return HttpResponse.notFound();
        }
    }

    // ===== 5. DELETE =====
    @Delete("/{id}")
    public HttpResponse<?> deleteOrder(@PathVariable String id) {
        try {
            orderService.deleteOrder(id);
            return HttpResponse.ok("Deleted successfully");
        } catch (Exception e) {
            return HttpResponse.badRequest("Delete failed: " + e.getMessage());
        }
    }
}