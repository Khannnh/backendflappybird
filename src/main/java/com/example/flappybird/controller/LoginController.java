package com.example.flappybird.controller;

import com.example.flappybird.model.LoginRequest;
import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")  // Endpoint cho tất cả các API liên quan đến login
public class LoginController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")  // Endpoint xử lý đăng nhập
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();

        // Tìm người chơi theo tên đăng nhập
        Player player = playerService.findByUsername(loginRequest.getUsername());

        // Kiểm tra xem người chơi có tồn tại và mật khẩu có khớp không
        if (player == null) {
            response.put("success", false);
            response.put("message", "Tên tài khoản không tồn tại!");
            return ResponseEntity.badRequest().body(response);
        }

        // So sánh mật khẩu đã mã hóa với mật khẩu người dùng nhập vào
        if (passwordEncoder.matches(loginRequest.getPassword(), player.getPw())) {
            response.put("success", true);
            response.put("message", "Đăng nhập thành công!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Mật khẩu không chính xác!");
            return ResponseEntity.badRequest().body(response);
        }
    }
}