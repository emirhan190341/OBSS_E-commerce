package com.obss_final_project.e_commerce.dto.response.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductResponse implements Serializable {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String logo;
    private Long sellerId;
    private Set<String> categories;

}
