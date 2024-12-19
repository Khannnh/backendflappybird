package com.example.flappybird.controller;

import com.example.flappybird.model.LoginRequest;
import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession; // Import HttpSession

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")  // Đường dẫn chung cho tất cả các API trong lớp này
public class LoginController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // Tìm kiếm người chơi trong cơ sở dữ liệu
        Player player = playerService.findByUsername(loginRequest.getUsername());
        if (player == null || !loginRequest.getPassword().equals(player.getPw())) {
            response.put("success", false);
            response.put("message", "Tên tài khoản hoặc mật khẩu không chính xác!");
            return ResponseEntity.status(401).body(response);  // Trả về lỗi 401 nếu không tìm thấy hoặc mật khẩu không chính xác
        }

        // Log thông tin để kiểm tra
        System.out.println("Username saved in session: " + player.getUsername());
        System.out.println("Ten saved in session: " + player.getTen());
        
        // Xác thực thành công, lưu thông tin vào session
        response.put("success", true);
        response.put("message", "Đăng nhập thành công!");
        response.put("username", player.getUsername());

        // Lưu thông tin vào session
        session.setAttribute("username", player.getUsername());
        session.setAttribute("ten", player.getTen());

        return ResponseEntity.ok(response);  // Trả về phản hồi thành công
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, String>> getUserInfo(HttpSession session) {
        Map<String, String> userInfo = new HashMap<>();
        String username = (String) session.getAttribute("username");
        String ten = (String) session.getAttribute("ten");

        if (username == null || ten == null) {
            userInfo.put("error", "Session không tồn tại hoặc đã hết hạn.");
        } else {
            userInfo.put("username", username);
            userInfo.put("ten", ten);
        }

        // Log thông tin để kiểm tra
        System.out.println("Username from session: " + username);
        System.out.println("Ten from session: " + ten);

        return ResponseEntity.ok(userInfo);
    }



    @GetMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // Hủy session để đăng xuất
        session.invalidate();

        response.put("success", true);
        response.put("message", "Đăng xuất thành công!");
        return ResponseEntity.ok(response);  // Trả về phản hồi thành công
    }
}