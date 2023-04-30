package com.course_sys.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI config() {
        {
            return new OpenAPI()
                    .info(new Info()
                            .title("Program API")
                            .description("Application")
                            .version("v0.0.1"))
                    .components(new Components()
                            .addSecuritySchemes("bearerAuth",
                                    new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT"))
                            .addHeaders("Authorization",
                                    new Header()
                                            .description("auth token")
                                            .required(true)));
        }
    }
    }
