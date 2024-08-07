package com.springbootstudy.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootstudy.app.domain.Order;
import com.springbootstudy.app.domain.OrderItem;
import com.springbootstudy.app.service.OrderService;
import com.springbootstudy.app.service.ProductService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WidgetController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderService orderService;
    private final ProductService productService;

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody, Model model) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);

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

        // 주문에 포함된 모든 제품에 대해 장바구니에서 삭제
        List<OrderItem> orderItems = orderService.findOrderItemsByOrderId(orderId);
        for (OrderItem item : orderItems) {
        	productService.deleteCart(item.getProductId());
        }

        model.addAttribute("order", order);
        return "views/shop/success";
    }


    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public String fail() {
        return "views/shop/fail";
    }
}
