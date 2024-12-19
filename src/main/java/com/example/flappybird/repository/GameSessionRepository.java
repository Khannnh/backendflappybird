package com.example.flappybird.repository;

import com.example.flappybird.model.GameSession;
import com.example.flappybird.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameSessionRepository extends JpaRepository<GameSession, Integer> {
    List<GameSession> findByPlayer(Player player);
}

/*kế thừa từ JpaRepository, cho phép bạn sử dụng các phương thức CRUD mà 
không cần phải viết mã cho chúng.*/