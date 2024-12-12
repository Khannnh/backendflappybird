package com.example.flappybird.repository;

import com.example.flappybird.model.GameSession;
import com.example.flappybird.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameSessionRepository extends JpaRepository<GameSession, Integer> {
    List<GameSession> findByPlayer(Player player);
}