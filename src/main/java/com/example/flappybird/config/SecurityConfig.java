package com.example.flappybird.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**", "/login.html", "/createaccount.html", 
                                 "/flappybird.png", "/flappybirdbg.png", "/favicon.ico", "/gameover.jpg")
                .permitAll()  // Cho phép truy cập không yêu cầu đăng nhập
                .requestMatchers("/home.html").authenticated()  // Chỉ cho phép truy cập home.html sau khi đăng nhập hoặc tạo tài khoản
                .anyRequest().authenticated()  // Các yêu cầu còn lại cần phải đăng nhập
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**") // Bỏ qua CSRF cho những endpoint API cụ thể
            )
            .formLogin(form -> form
                .loginPage("/login.html")  // Đặt trang login tùy chỉnh
                .defaultSuccessUrl("/home.html", true)  // Chuyển hướng đến trang home.html sau khi đăng nhập thành công
                .permitAll()  // Cho phép mọi người truy cập trang login
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Sử dụng session khi cần thiết
            );

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Cấu hình CORS cho các endpoint bắt đầu bằng /api/
                .allowedOrigins("http://localhost:8080")  // Địa chỉ frontend của bạn
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức HTTP cho phép
                .allowedHeaders("*")  // Cho phép các header tùy chỉnh
                .allowCredentials(true);  // Cho phép gửi cookies nếu cần thiết
    }
}
