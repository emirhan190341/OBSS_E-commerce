package com.obss_final_project.e_commerce.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchInputRequest {

    @NotNull
    private String keyword;
}
