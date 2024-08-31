package com.obss_final_project.e_commerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Emirhan Arici",
                        url = "https://external.obss.com.tr/bitbucket/projects/JIPG224/repos/emirhan.arici/browse/e-commerce"
                ),
                description = "OBSS Final Project " +
                        "(Spring Boot, Spring Security , MySQL,Integration Test, Docker) ",
                title = "E-Commerce App API",
                version = "1.0.0"
        )
)
public class OpenApiConfig {
}

