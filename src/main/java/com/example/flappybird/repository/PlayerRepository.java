package com.example.flappybird.repository;

import com.example.flappybird.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findByUsername(String username);
}