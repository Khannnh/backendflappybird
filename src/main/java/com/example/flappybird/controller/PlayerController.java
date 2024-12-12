package com.example.flappybird.controller;

import com.example.flappybird.model.Player;
import com.example.flappybird.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/register")
    public Player registerPlayer(@RequestParam String ten, @RequestParam String username, @RequestParam String pw) {
        return playerService.registerPlayer(ten, username, pw);
    }

    @GetMapping("/{username}")
    public Player getPlayerByUsername(@PathVariable String username) {
        return playerService.findByUsername(username);
    }
}