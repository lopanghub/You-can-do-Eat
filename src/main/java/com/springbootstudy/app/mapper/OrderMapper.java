package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.springbootstudy.app.domain.Order;
import com.springbootstudy.app.domain.OrderItem;

@Mapper
public interface OrderMapper {
	 @Insert("INSERT INTO orders (order_id, member_id, total_amount, status) VALUES (#{orderId}, #{memberId}, #{totalAmount}, #{status})")
	    void insertOrder(Order order);

	    @Select("SELECT * FROM orders WHERE member_id = #{memberId}")
	    List<Order> findOrdersByMemberId(String memberId);
	    
	    @Insert("INSERT INTO order_items (order_id, product_id, product_name, product_image, price, quantity) VALUES (#{orderId}, #{productId}, #{productName}, #{productImage}, #{price}, #{quantity})")
	    void insertOrderItem(OrderItem orderItem);

	    @Select("SELECT * FROM order_items WHERE order_id = #{orderId}")
	    List<OrderItem> findOrderItemsByOrderId(@Param("orderId") String orderId);
	    
	    @Select("SELECT * FROM orders WHERE order_id = #{orderId}")
	    Order findOrderByOrderId(@Param("orderId") String orderId);
}
