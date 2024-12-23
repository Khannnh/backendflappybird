package com.example.flappybird.controller;

import com.example.flappybird.model.LoginRequest;
import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // Tìm kiếm người chơi trong cơ sở dữ liệu (case-insensitive)
        Player player = playerService.findByUsername(loginRequest.getUsername().toLowerCase());
        if (player == null) {
            response.put("success", false);
            response.put("message", "Tài khoản không tồn tại!");
            return ResponseEntity.status(401).body(response);
        }

        if (!loginRequest.getPassword().equals(player.getPassword())) {
            response.put("success", false);
            response.put("message", "Mật khẩu không chính xác!");
            return ResponseEntity.status(401).body(response);
        }

        // Xác thực thành công
        response.put("success", true);
        response.put("message", "Đăng nhập thành công!");
        response.put("username", player.getUsername());

        // Lưu thông tin vào session
        session.setAttribute("username", player.getUsername());
        session.setAttribute("name", player.getName());

        // Log thông tin để kiểm tra
        System.out.println("Username saved in session: " + player.getUsername());
        System.out.println("Ten saved in session: " + player.getName());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, String>> getUserInfo(HttpSession session) {
        Map<String, String> userInfo = new HashMap<>();
        String username = (String) session.getAttribute("username");
        String name = (String) session.getAttribute("name");

        if (username == null || name == null) {
            userInfo.put("error", "Session không tồn tại hoặc đã hết hạn.");
        } else {
            userInfo.put("username", username);
            userInfo.put("name", name);
        }

        // Log thông tin để kiểm tra
        System.out.println("Username from session: " + username);
        System.out.println("Name from session: " + name);

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String username = (String) session.getAttribute("username");

        // Hủy session
        session.invalidate();

        // Ghi log thông tin đăng xuất
        System.out.println("User  " + username + " has logged out.");

        response.put("success", true);
        response.put("message", "Đăng xuất thành công!");
        response.put("username", username); // Gửi lại thông tin username đã đăng xuất
        return ResponseEntity.ok(response);
    }
    //kiểm tra đã đăng xuất chưa
    @GetMapping("/check-session")
    public ResponseEntity<Map<String, Boolean>> checkSession(HttpSession session) {
        Map<String, Boolean> response = new HashMap<>();
        String username = (String) session.getAttribute("username");

        // Kiểm tra xem session có còn tồn tại không
        response.put("loggedIn", username != null);
        return ResponseEntity.ok(response);
    }
}
