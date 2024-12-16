package com.example.flappybird.service;

import com.example.flappybird.model.Player;
import com.example.flappybird.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Player registerPlayer(String ten, String username, String pw) {
        // Kiểm tra xem username đã tồn tại chưa
        if (playerRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        // Kiểm tra độ dài tên người dùng
        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("Tên người dùng phải có độ dài từ 3 đến 20 ký tự");
        }

        Player player = new Player();
        player.setTen(ten);
        player.setUsername(username);
        player.setPw(passwordEncoder.encode(pw)); // Mã hóa mật khẩu trước khi lưu
        return playerRepository.save(player);
    }

    public Player findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public boolean validateLogin(String username, String rawPassword) {
        Player player = findByUsername(username);
        if (player != null) {
            return passwordEncoder.matches(rawPassword, player.getPw());
        }
        return false; // Tên người dùng không tồn tại
    }
}