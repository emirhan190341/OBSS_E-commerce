package com.obss_final_project.e_commerce.dto.request.product;

import lombok.Data;

import java.util.List;

@Data
public class SearchDtoRequest {
    private List<String> fieldName;
    private List<String> searchValue;
}
