package com.example.flappybird.controller;

import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerPlayer(@RequestBody Map<String, String> playerMap) {
        String ten = playerMap.get("ten");
        String username = playerMap.get("username");
        String pw = playerMap.get("pw");
        Player player = playerService.registerPlayer(ten, username, pw);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/{username}")
    public Player getPlayerByUsername(@PathVariable String username) {
        return playerService.findByUsername(username);
    }
}