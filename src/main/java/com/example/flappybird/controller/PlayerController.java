package com.example.flappybird.controller;

import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    
    // API đăng ký người chơi
    @PostMapping("/register")
    public ResponseEntity<String> registerPlayer(@RequestBody Player player) {
        try {
            // Gọi service để đăng ký người chơi
            playerService.registerPlayer(player.getTen(), player.getUsername(), player.getPw());
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
}
