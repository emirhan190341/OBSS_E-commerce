package com.obss_final_project.e_commerce.service.impl;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.response.cart.CartResponse;
import com.obss_final_project.e_commerce.email.EmailService;
import com.obss_final_project.e_commerce.email.EmailTemplateName;
import com.obss_final_project.e_commerce.exception.product.ProductNotFoundException;
import com.obss_final_project.e_commerce.exception.user.UserNotFoundException;
import com.obss_final_project.e_commerce.mapper.CartMapper;
import com.obss_final_project.e_commerce.model.Cart;
import com.obss_final_project.e_commerce.model.Product;
import com.obss_final_project.e_commerce.model.User;
import com.obss_final_project.e_commerce.repository.CartRepository;
import com.obss_final_project.e_commerce.repository.ProductRepository;
import com.obss_final_project.e_commerce.repository.UserRepository;
import com.obss_final_project.e_commerce.service.CartService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.email-order}")
    private String orderTopic;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository,
                           EmailService emailService,
                           KafkaTemplate<String, Object> kafkaTemplate) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public CartResponse addProductToCart(Long userId, UUID productId, int quantity) {
        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductId(userId, productId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Cart cart;

        if (product.getQuantity() < quantity) {
            throw new ProductNotFoundException("Product quantity is not enough");
        }

        if (existingCartItem.isPresent()) {
            cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cart.setPrice(cart.getPrice().add(product.getPrice().multiply(BigDecimal.valueOf(quantity))));
        } else {
            cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setImageUrl(product.getLogo());
            cart.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.setUser(new User(userId));
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        return CartMapper.toCartResponse(cartRepository.save(cart));
    }

    @Override
    public CustomPageResponse<CartResponse> getCartByUserId(Long userId, int pageNumber, int pageSize) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Cart> cartPage = cartRepository.findByUserId(userId, pageable);
        return CustomPageResponse.of(cartPage.map(CartMapper::toCartResponse));
    }

    @Override
    @Transactional
    public String deleteAllProductsFromCart(Long userId) throws MessagingException {

        List<Cart> cartItems = cartRepository.findAllByUserId(userId);

        if (cartItems.isEmpty()) {
            return "Your cart is empty.";
        }

        sendSuccessEmail(cartItems);

        kafkaTemplate.send(orderTopic, generateOrderNumber());

        cartRepository.deleteAllByUserId(userId);
        return "All products are deleted from cart";
    }

    private void sendSuccessEmail(List<Cart> cartItems) throws MessagingException {

        BigDecimal total = cartItems.stream()
                .map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> properties = new HashMap<>();
        properties.put("cartItems", cartItems);
        properties.put("total", total);
        properties.put("orderNumber", generateOrderNumber());

        emailService.sendEmail(
                "emirhan190341@gmail.com",
                EmailTemplateName.ORDER_SUCCESS,
                "Order Confirmation",
                properties
        );
    }

    private String generateOrderNumber() {
        return UUID.randomUUID().toString();
    }
}
