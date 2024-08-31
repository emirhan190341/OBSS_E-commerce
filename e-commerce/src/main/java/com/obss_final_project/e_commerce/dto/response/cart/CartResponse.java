package com.obss_final_project.e_commerce.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse implements Serializable {

    private UUID id;
    private UUID productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private BigDecimal price;

}
