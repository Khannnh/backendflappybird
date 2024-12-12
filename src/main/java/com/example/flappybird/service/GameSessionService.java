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

    public GameSession startNewSession(Player player) {
        GameSession session = new GameSession();
        session.setPlayer(player);
        session.setPlayDate(LocalDateTime.now());
        return gameSessionRepository.save(session);
    }

    public GameSession endSession(GameSession session, int score) {
        session.setScore(score);
        return gameSessionRepository.save(session);
    }

    public List<GameSession> getSessionsByPlayer(Player player) {
        return gameSessionRepository.findByPlayer(player);
    }

	public GameSession getSessionById(int sessionId) {
		// TODO Auto-generated method stub
		return null;
	}
}