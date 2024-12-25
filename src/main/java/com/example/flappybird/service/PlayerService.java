
package com.example.flappybird.service;

import com.example.flappybird.model.GameSession;
import com.example.flappybird.model.Player;
import com.example.flappybird.repository.GameSessionRepository;
import com.example.flappybird.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//xử lý logic , tiện cho kiểm thử 
@Service
public class PlayerService {
    @Autowired
    //khai báo biến thành viên của lớp hiện tại
    private PlayerRepository playerRepository;
    private GameSessionRepository gameSessionRepository;


    // Phương thức để lưu người chơi
    public void savePlayer(Player player) {
        playerRepository.save(player); // Lưu người chơi vào cơ sở dữ liệu
    }
    
    public Player registerPlayer(String name, String username, String password) {
        // Kiểm tra xem username đã tồn tại chưa
        if (playerRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        // Kiểm tra độ dài tên người dùng
        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("Tên người dùng phải có độ dài từ 3 đến 20 ký tự");
        }

        Player player = new Player();
        player.setName(name);
        player.setUsername(username);
        player.setPassword(password);  // Lưu mật khẩu không mã hóa
        player.setLives(4);  // Đặt giá trị mặc định cho lives
        
        if (player.getPassword() == null || player.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return playerRepository.save(player);
    }

    public Player findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public boolean validateLogin(String username, String rawPassword) {
        Player player = findByUsername(username);
        if (player != null) {
            // Không sử dụng passwordEncoder vì không mã hóa mật khẩu
            return player.getPassword().equals(rawPassword); // So sánh mật khẩu trực tiếp
        }
        return false; // Tên người dùng không tồn tại
    }

    public Player findById(int player_id) {
        return playerRepository.findById(player_id)
                .orElseThrow(() -> new IllegalArgumentException("Player ID không tồn tại: " + player_id));
    }
    public List<Player> getTop20Players() {
        // Sắp xếp theo tổng điểm giảm dần và id tăng dần
        List<Player> players = playerRepository.findAll(Sort.by(Sort.Order.desc("total_point"), Sort.Order.asc("id")));

        // Giới hạn chỉ lấy 20 người chơi đầu tiên
        if (players.size() > 20) {
            return players.subList(0, 20);  // Lấy 20 người chơi đầu tiên
        }
        return players;  // Nếu có ít hơn 20 người, trả về toàn bộ danh sách
    }
    public Integer getTotalPointsByPlayerId(int playerId) {
        Integer totalPoints = playerRepository.findTotalPointsByPlayerId(playerId);
        return totalPoints != null ? totalPoints : 0; // Trả về 0 nếu không có dữ liệu.
    }
    
}