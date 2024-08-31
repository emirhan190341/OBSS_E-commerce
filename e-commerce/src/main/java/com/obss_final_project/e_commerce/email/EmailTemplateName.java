package com.obss_final_project.e_commerce.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ORDER_SUCCESS("order_success"),
    ORDER_CONFIRMATION("order_confirmation");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
