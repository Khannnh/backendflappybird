package com.example.flappybird.service;
import com.example.flappybird.model.GameSession;
import com.example.flappybird.repository.GameSessionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {
    @Autowired
    private GameSessionRepository gameSessionRepository;

    // Phương thức để lưu phiên chơi
    public void saveGameSession(GameSession session) {
        gameSessionRepository.save(session);
    }
    // Phương thức để lấy 20 phiên chơi gần nhất
    public List<GameSession> getLast20Sessions() {
        return gameSessionRepository.findLast20Sessions(PageRequest.of(0, 20));
    }
    //phương thức để tìm người chơi theo id
    public List<GameSession> findByPlayerId(int playerId) {
        return gameSessionRepository.findByPlayerId(playerId);
    }

}