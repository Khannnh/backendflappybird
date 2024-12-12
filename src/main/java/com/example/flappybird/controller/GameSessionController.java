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

    @PostMapping("/start")
    public GameSession startNewSession(@RequestParam String username) {
        Player player = playerService.findByUsername(username);
        return gameSessionService.startNewSession(player);
    }

    @PostMapping("/end")
    public GameSession endSession(@RequestParam int sessionId, @RequestParam int score) {
        GameSession session = gameSessionService.getSessionById(sessionId);
        return gameSessionService.endSession(session, score);
    }

    @GetMapping("/player/{username}")
    public List<GameSession> getSessionsByPlayer(@PathVariable String username) {
        Player player = playerService.findByUsername(username);
        return gameSessionService.getSessionsByPlayer(player);
    }
}