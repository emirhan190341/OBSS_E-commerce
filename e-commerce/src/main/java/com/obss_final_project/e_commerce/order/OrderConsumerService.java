package com.obss_final_project.e_commerce.order;


import com.obss_final_project.e_commerce.email.EmailService;
import com.obss_final_project.e_commerce.email.EmailTemplateName;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderConsumerService {

    private final EmailService emailService;

    @Autowired
    public OrderConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "order-success", groupId = "order-group")
    public void listen(String message) throws MessagingException {

        sendOrderConfirmationEmail(message);
    }

    private String extractOrderNumber(String message) {
        return message.replace("Order Number: ", "").trim();
    }

    private void sendOrderConfirmationEmail(String orderNumber) throws MessagingException {

        Map<String, Object> properties = new HashMap<>();
        properties.put("orderNumber", orderNumber);
        properties.put("orderDate", "2024-08-15");

        emailService.sendEmail(
                "emirhan190341@gmail.com",
                EmailTemplateName.ORDER_CONFIRMATION,
                "Order Shipped!",
                properties
        );

    }

}
