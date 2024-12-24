package com.example.flappybird.repository;

import com.example.flappybird.model.GameSession;
import com.example.flappybird.model.Player;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameSessionRepository extends JpaRepository<GameSession, Integer> {
    List<GameSession> findByPlayer(Player player);
    @Query("SELECT gs FROM GameSession gs ORDER BY gs.playDate DESC")
    
    List<GameSession> findLast20Sessions(Pageable pageable);
    @Query("SELECT gs FROM GameSession gs WHERE gs.player.id = :playerId")
    
    List<GameSession> findByPlayerId(@Param("playerId") int playerId);
}


/*kế thừa từ JpaRepository, cho phép bạn sử dụng các phương thức CRUD mà 
không cần phải viết mã cho chúng.*/