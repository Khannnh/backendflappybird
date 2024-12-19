package com.example.flappybird.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")  // Địa chỉ frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức
                .allowedHeaders("*")  // Cho phép các header
                .allowCredentials(true);  // Hỗ trợ cookie và thông tin xác thực
    }
}
