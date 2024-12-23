package com.example.flappybird.service;

import com.example.flappybird.model.GameSession;
import com.example.flappybird.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {
    @Autowired
    private GameSessionRepository gameSessionRepository;

    // Phương thức để lưu phiên chơi
    public void saveGameSession(GameSession session) {
        gameSessionRepository.save(session);
    }
}