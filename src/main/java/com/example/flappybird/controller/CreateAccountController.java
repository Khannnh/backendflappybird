
package com.example.flappybird.controller;

import com.example.flappybird.model.Player;
import com.example.flappybird.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CreateAccountController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Player newPlayer) {
        // Kiểm tra username đã tồn tại
        if (playerRepository.findByUsername(newPlayer.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username đã được sử dụng!");
        }

        // Kiểm tra độ dài của username, password và tên
        if (newPlayer.getUsername().length() < 5 || newPlayer.getUsername().length() > 20) {
            return ResponseEntity.badRequest().body("Username phải có độ dài từ 5 đến 20 ký tự.");
        }

        if (newPlayer.getPw().length() < 6 || newPlayer.getPw().length() > 20) {
            return ResponseEntity.badRequest().body("Password phải có độ dài từ 6 đến 20 ký tự.");
        }

        if (newPlayer.getTen().length() < 3 || newPlayer.getTen().length() > 50) {
            return ResponseEntity.badRequest().body("Tên phải có độ dài từ 3 đến 50 ký tự.");
        }

        // Thiết lập điểm ban đầu
        newPlayer.setTotalPoint(0);

        // Lưu thông tin người chơi vào cơ sở dữ liệu
        playerRepository.save(newPlayer);

        return ResponseEntity.ok().body("Tạo tài khoản thành công!");
    }
}
