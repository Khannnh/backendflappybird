package com.example.flappybird.controller;

import com.example.flappybird.model.GameOverRequest;
import com.example.flappybird.model.GameSession;
import com.example.flappybird.model.Player;
import com.example.flappybird.service.GameSessionService;
import com.example.flappybird.service.PlayerService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private PlayerService playerService;

    // API để lưu thông tin phiên chơi
    @PostMapping("/gameover")
    public ResponseEntity<String> saveGameOverInfo(@RequestBody GameOverRequest request) {
        // Tìm người chơi theo ID
        Player player = playerService.findById(request.getPlayer_id());
        if (player == null) {
            return ResponseEntity.badRequest().body("Người chơi không tồn tại");
        }

        // Tạo một phiên chơi mới
        GameSession session = new GameSession();
        session.setPlayer(player);
        session.setScore(request.getScore());
        session.setPlayDate(request.getPlay_date() != null ? request.getPlay_date() : LocalDateTime.now());

        // Lưu phiên chơi vào cơ sở dữ liệu trước
        gameSessionService.saveGameSession(session);

        // Lấy lại danh sách các phiên chơi (bao gồm phiên chơi mới vừa lưu)
        List<GameSession> sessions = gameSessionService.findByPlayerId(player.getId());

        // Tính tổng điểm của tất cả các phiên chơi
        int total_point = sessions.stream()
                                  .mapToInt(GameSession::getScore)
                                  .sum();

        // Log thông tin để kiểm tra
        System.out.println("Player ID: " + player.getId());
        System.out.println("Sessions retrieved: " + sessions);
        System.out.println("Total points calculated: " + total_point);

        // Cập nhật total_point của người chơi
        player.setTotal_point(total_point);
        playerService.savePlayer(player);

        return ResponseEntity.ok("Thông tin phiên chơi đã được lưu thành công. Tổng điểm: " + total_point);
    }
    @GetMapping("/history") // Phương thức GET để lấy lịch sử phiên chơi
    public ResponseEntity<List<GameSession>> getLast20GameSessions() {
        List<GameSession> sessions = gameSessionService.getLast20Sessions();
        return ResponseEntity.ok(sessions);
    }

}
