package com.example.flappybird.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/BaiTapLon/**").permitAll()  // Cho phép truy cập các file trong thư mục BaiTapLon
                .requestMatchers("/api/**").permitAll()  // Cho phép truy cập đến các endpoint bắt đầu bằng /api/
                .requestMatchers("/api/login").permitAll()
                .anyRequest().authenticated()  // Các yêu cầu khác cần xác thực
            )
            .csrf(csrf -> csrf.disable())  // Tắt CSRF nếu không cần thiết
            .formLogin(login -> login.disable());  // Tắt form login mặc định của Spring Security

        return http.build();
    }
}