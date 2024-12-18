
package com.example.flappybird.service;

import com.example.flappybird.model.Player;
import com.example.flappybird.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    // Không sử dụng PasswordEncoder nữa
    // @Autowired
    // private PasswordEncoder passwordEncoder;

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
        player.setPw(pw);  // Lưu mật khẩu không mã hóa
        return playerRepository.save(player);
    }

    public Player findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public boolean validateLogin(String username, String rawPassword) {
        Player player = findByUsername(username);
        if (player != null) {
            // Không sử dụng passwordEncoder vì không mã hóa mật khẩu
            return player.getPw().equals(rawPassword); // So sánh mật khẩu trực tiếp
        }
        return false; // Tên người dùng không tồn tại
    }
}
