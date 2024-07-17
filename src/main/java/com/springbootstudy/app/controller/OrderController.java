package com.springbootstudy.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootstudy.app.domain.Order;
import com.springbootstudy.app.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/saveOrder")
    public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody Map<String, Object> orderData) {
        boolean isSaved = orderService.saveOrder(orderData);
        Map<String, Object> response = new HashMap<>();
        response.put("success", isSaved);
        if (!isSaved) {
            response.put("message", "Order save failed");
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(@RequestParam("orderId") String orderId, Model model) {
        System.out.println("OrderId: " + orderId);  // 로그 추가
        Order order = orderService.findOrderByOrderId(orderId);
        if (order == null) {
            System.out.println("Order not found");  // 로그 추가
            throw new RuntimeException("Order not found with orderId: " + orderId);
        }
        System.out.println("Order found: " + order);  // 로그 추가
        model.addAttribute("order", order);
        return "views/shop/success";
    }

    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public String fail() {
        return "views/shop/fail";
    }
}

