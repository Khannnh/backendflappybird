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
                .requestMatchers("/api/**", "/login.html", "/createaccount.html","/home.html" ,"/gameover.html",
                                 "/flappybird.png", "/flappybirdbg.png", "/gameover.jpg" , "leaerboard.html" , "/main.html")
                .permitAll()  // Cho phép truy cập không yêu cầu đăng nhập
                .requestMatchers("/home.html").authenticated()  // home.html chỉ cho phép sau khi đăng nhập
                .anyRequest().authenticated()  // Các yêu cầu khác cần đăng nhập
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")  // Tắt CSRF cho API
            )
            .formLogin(form -> form
                .loginPage("/login.html")  // Đặt trang login tùy chỉnh
                .defaultSuccessUrl("/home.html", true)  // Chuyển hướng đến home.html khi đăng nhập thành công
                .failureUrl("/login.html?error=true")  // Xử lý lỗi login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Endpoint logout
                .logoutSuccessUrl("/login.html")  // Chuyển hướng sau khi logout
                .invalidateHttpSession(true)  // Xóa session
                .deleteCookies("JSESSIONID")  // Xóa cookie phiên
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Dùng session khi cần
            );

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")  // Địa chỉ frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức
                .allowedHeaders("*")  // Cho phép các header
                .allowCredentials(true);  // Hỗ trợ cookie và thông tin xác thực
    }
}