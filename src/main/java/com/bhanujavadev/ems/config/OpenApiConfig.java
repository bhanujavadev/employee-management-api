package com.bhanujavadev.ems.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Employee Management API")

                        .description("""
                                Enterprise Employee Management System
                                
                                Features:
                                • Department Management
                                • Employee Management
                                • Role Management
                                • User Management
                                • JWT Authentication
                                • Spring Security
                                • Swagger Documentation
                                """)

                        .version("v1.0")

                        .contact(new Contact()
                                .name("Bhanupriya Kunchem")
                                .email("bhanukuchem801@gmail.com")
                                .url("https://github.com/bhanujavadev"))

                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/bhanujavadev/employee-management-api"));
    }
}