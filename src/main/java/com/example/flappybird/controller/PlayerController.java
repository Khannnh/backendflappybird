
package com.example.flappybird.controller;

import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Trả về dữ liệu JSON hoặc XML
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

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

    //API của trang bảng xếp hạng lấy 20 ng điểm cao nhất
    @GetMapping("/leaderboard")
    public ResponseEntity<List<Player>> getTop20Players() {
        List<Player> topPlayers = playerService.getTop20Players();
        return ResponseEntity.ok(topPlayers);
    }
}
