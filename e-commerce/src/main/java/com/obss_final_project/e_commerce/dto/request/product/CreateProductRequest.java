package com.obss_final_project.e_commerce.dto.request.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class CreateProductRequest implements Serializable {


    @NotNull
    @Size(min = 2, max = 20 )
    private String name;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;
    @NotNull
    private Set<String> categories;

}
