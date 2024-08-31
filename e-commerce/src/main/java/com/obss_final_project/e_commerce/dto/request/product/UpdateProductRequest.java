package com.obss_final_project.e_commerce.dto.request.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class UpdateProductRequest implements Serializable {


    private String name;

    private String description;

    private BigDecimal price;

    private int quantity;

    private String logo;

    private Set<String> categories;

}
