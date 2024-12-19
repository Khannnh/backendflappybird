package com.example.flappybird.controller;

import com.example.flappybird.model.GameSession;
import com.example.flappybird.model.Player;
import com.example.flappybird.service.GameSessionService;
import com.example.flappybird.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class GameSessionController {
    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private PlayerService playerService;

    // Bắt đầu một phiên chơi mới
    @PostMapping("/start")
    public GameSession startNewSession(@RequestParam String username) {
        Player player = playerService.findByUsername(username);
        if (player == null) {
            throw new IllegalArgumentException("Người chơi không tồn tại");
        }
        return gameSessionService.startNewSession(player);
    }

    // Kết thúc một phiên chơi
    @PostMapping("/end")
    public GameSession endSession(@RequestParam int sessionId, @RequestParam int score) {
        GameSession session = gameSessionService.getSessionById(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("Phiên chơi không tồn tại");
        }
        return gameSessionService.endSession(session, score);
    }

    // Lấy danh sách các phiên chơi của người chơi
    @GetMapping("/player/{username}")
    public List<GameSession> getSessionsByPlayer(@PathVariable String username) {
        Player player = playerService.findByUsername(username);
        if (player == null) {
            throw new IllegalArgumentException("Người chơi không tồn tại");
        }
        return gameSessionService.getSessionsByPlayer(player);
    }

    // Lấy phiên chơi theo ID
    @GetMapping("/{sessionId}")
    public GameSession getSessionById(@PathVariable int sessionId) {
        GameSession session = gameSessionService.getSessionById(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("Phiên chơi không tồn tại");
        }
        return session;
    }

    // Lấy tổng điểm của người chơi
    @GetMapping("/total-score")
    public int getTotalScore(@RequestParam String username) {
        Player player = playerService.findByUsername(username);
        if (player == null) {
            throw new IllegalArgumentException("Người chơi không tồn tại");
        }
        return gameSessionService.getTotalScoreByPlayer(player);
    }
}