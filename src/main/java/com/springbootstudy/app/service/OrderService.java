package com.springbootstudy.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootstudy.app.domain.Order;
import com.springbootstudy.app.domain.OrderItem;
import com.springbootstudy.app.mapper.OrderMapper;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Transactional
    public boolean saveOrder(Map<String, Object> orderData) {
        try {
            Order order = new Order();
            order.setOrderId((String) orderData.get("orderId"));
            order.setMemberId((String) orderData.get("memberEmail"));
            order.setTotalAmount((int) orderData.get("amount"));
            order.setStatus("COMPLETED");

            orderMapper.insertOrder(order);

            List<Map<String, Object>> items = (List<Map<String, Object>>) orderData.get("items");
            for (Map<String, Object> itemData : items) {
                OrderItem item = new OrderItem();
                item.setOrderId(order.getOrderId());
                item.setProductId((int) itemData.get("productId"));
                item.setProductName((String) itemData.get("productName"));
                item.setPrice((int) itemData.get("price"));
                item.setQuantity((int) itemData.get("quantity"));
                item.setProductImage((String) itemData.get("productImage"));
                orderMapper.insertOrderItem(item);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> findOrdersByMemberId(String memberId) {
        return orderMapper.findOrdersByMemberId(memberId);
    }
    
    public Order findOrderByOrderId(String orderId) {
        Order order = orderMapper.findOrderByOrderId(orderId);
        if (order != null) {
            List<OrderItem> orderItems = orderMapper.findOrderItemsByOrderId(orderId);
            order.setOrderItems(orderItems);
        }
        System.out.println("Order found by service: " + order);  // 로그 추가
        return order;
    }
    
    public List<OrderItem> findOrderItemsByOrderId(String orderId) {
        return orderMapper.findOrderItemsByOrderId(orderId);
    }


}
