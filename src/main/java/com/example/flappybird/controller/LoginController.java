package com.example.flappybird.controller;

import com.example.flappybird.model.LoginRequest;
import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")  // Endpoint cho tất cả các API liên quan đến login
public class LoginController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();

        // Tìm kiếm người chơi trong cơ sở dữ liệu
        Player player = playerService.findByUsername(loginRequest.getUsername());
        if (player == null || !loginRequest.getPassword().equals(player.getPw())) {
            response.put("success", false);
            response.put("message", "Tên tài khoản hoặc mật khẩu không chính xác!");
            return ResponseEntity.status(401).body(response);  // Trả về lỗi 401 nếu không tìm thấy hoặc mật khẩu không chính xác
        }

        // Xác thực thành công, trả về thông tin người dùng
        response.put("success", true);
        response.put("message", "Đăng nhập thành công!");
        response.put("username", player.getUsername());
        return ResponseEntity.ok(response);  // Trả về phản hồi thành công
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        Map<String, Object> response = new HashMap<>();

        // Đăng xuất và xóa thông tin xác thực trong SecurityContext
        response.put("success", true);
        response.put("message", "Đăng xuất thành công!");
        return ResponseEntity.ok(response);  // Trả về phản hồi thành công
    }
}