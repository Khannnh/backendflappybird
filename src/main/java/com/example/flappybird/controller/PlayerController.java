package com.example.flappybird.controller;

import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Trả về dữ liệu JSON hoặc XML
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // API đăng ký người chơi
    @PostMapping("/register")
    public ResponseEntity<String> registerPlayer(@RequestBody Player player) {
        try {
            // Gọi service để đăng ký người chơi
            playerService.registerPlayer(player.getName(), player.getUsername(), player.getPassword());
            return ResponseEntity.ok("Tạo tài khoản thành công!");
        } catch (IllegalArgumentException e) {
            // Nếu gặp lỗi, trả về thông báo lỗi
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API để lấy thông tin người chơi theo username
    @GetMapping("/{username}")
    public ResponseEntity<Player> getPlayerByUsername(@PathVariable String username) {
        Player player = playerService.findByUsername(username);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy người chơi
        }
    }

    // API đăng nhập người chơi
    @PostMapping("/login")
    public ResponseEntity<String> loginPlayer(@RequestBody Player player, HttpSession session) {
        boolean isValidLogin = playerService.validateLogin(player.getUsername(), player.getPassword());
        if (isValidLogin) {
            // Lưu thông tin người chơi vào session nếu đăng nhập thành công
            session.setAttribute("username", player.getUsername());
            return ResponseEntity.ok("Đăng nhập thành công!");
        } else {
            return ResponseEntity.badRequest().body("Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }

    // API để đăng xuất người chơi (xóa session)
    @PostMapping("/logout")
    public ResponseEntity<String> logoutPlayer(HttpSession session) {
        session.invalidate(); // Hủy bỏ session
        return ResponseEntity.ok("Đã đăng xuất!");
    }
}
