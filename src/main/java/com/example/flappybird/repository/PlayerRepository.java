package com.example.flappybird.repository;

import com.example.flappybird.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findByUsername(String username); // Tìm người chơi theo tên người dùng
}
//một interface sử dụng Spring Data JPA để tương tác với cơ sở dữ liệu.
//tương tác trực tiếp với csdl 
//thực hiện curd với csdl 