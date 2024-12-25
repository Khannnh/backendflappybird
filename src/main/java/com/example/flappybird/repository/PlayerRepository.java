
package com.example.flappybird.repository;

import com.example.flappybird.model.Player;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findByUsername(String username); // Tìm người chơi theo tên người dùng
    List<Player> findAll(Sort sort);
    
    @Query("SELECT p.total_point FROM Player p WHERE p.id = :playerId")
    Integer findTotalPointsByPlayerId(@Param("playerId") int playerId);
}
//một interface sử dụng Spring Data JPA để tương tác với cơ sở dữ liệu.
//tương tác trực tiếp với csdl 
//thực hiện curd với csdl 