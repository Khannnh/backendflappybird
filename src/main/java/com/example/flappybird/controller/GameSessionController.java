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
    @PostMapping("/gameover") // Đường dẫn mới cho gameover
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

        // Kiểm tra và lưu playDate (nếu có) từ request, nếu không thì sử dụng thời gian hiện tại
        if (request.getPlay_date() != null) {
            session.setPlayDate(request.getPlay_date()); // Lấy thời gian từ request
        } else {
            session.setPlayDate(LocalDateTime.now()); // Nếu không có playDate, sử dụng thời gian hiện tại
        }

        // Lưu phiên chơi vào cơ sở dữ liệu
        gameSessionService.saveGameSession(session);

        return ResponseEntity.ok("Thông tin phiên chơi đã được lưu thành công");
    }
    @GetMapping("/history") // Phương thức GET để lấy lịch sử phiên chơi
    public ResponseEntity<List<GameSession>> getLast20GameSessions() {
        List<GameSession> sessions = gameSessionService.getLast20Sessions();
        return ResponseEntity.ok(sessions);
    }

}
