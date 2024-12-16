package com.example.flappybird.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/BaiTapLon/**", "/api/**", "/api/login", "/login.html").permitAll()  // Cho phép truy cập không cần xác thực
                .anyRequest().authenticated()  // Các yêu cầu còn lại cần xác thực
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**") // Bỏ qua CSRF cho những endpoint API cụ thể
            )
            .formLogin(form -> form
                .loginPage("/login.html")  // Xác định trang đăng nhập của bạn
                .loginProcessingUrl("/api/login")  // URL xử lý form login
                .permitAll()  // Cho phép tất cả truy cập vào trang đăng nhập
            );

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Cấu hình CORS cho các endpoint bắt đầu bằng /api/
                .allowedOrigins("http://localhost:8080")  // Địa chỉ frontend của bạn
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức HTTP cho phép
                .allowCredentials(true);  // Cho phép gửi cookies nếu cần thiết
    }
}
