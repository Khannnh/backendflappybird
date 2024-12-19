package com.example.flappybird.service;

import com.example.flappybird.model.GameSession;
import com.example.flappybird.model.Player;
import com.example.flappybird.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameSessionService {
    @Autowired
    private GameSessionRepository gameSessionRepository;

    // Bắt đầu một phiên chơi mới
    public GameSession startNewSession(Player player) {
        GameSession session = new GameSession();
        session.setPlayer(player);
        session.setPlayDate(LocalDateTime.now());
        return gameSessionRepository.save(session);
    }

    // Kết thúc một phiên chơi
    public GameSession endSession(int sessionId, int score) {
        GameSession session = getSessionById(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("Phiên chơi không tồn tại");
        }
        session.setScore(score);
        session.setPlayDate(LocalDateTime.now()); // Cập nhật thời gian chơi
        return gameSessionRepository.save(session);
    }

    // Lấy danh sách các phiên chơi của người chơi
    public List<GameSession> getSessionsByPlayer(Player player) {
        return gameSessionRepository.findByPlayer(player);
    }

    // Lấy phiên chơi theo ID
    public GameSession getSessionById(int sessionId) {
        return gameSessionRepository.findById(sessionId).orElse(null);
    }

    // Lấy tổng điểm của người chơi
    public int getTotalScoreByPlayer(Player player) {
        List<GameSession> sessions = getSessionsByPlayer(player);
        return sessions.stream().mapToInt(GameSession::getScore).sum();
    }
}