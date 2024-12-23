package com.example.flappybird.controller;

import com.example.flappybird.model.Player;
import com.example.flappybird.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CreateAccountController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Player newPlayer) {
        Map<String, String> response = new HashMap<>();

        // Kiểm tra các trường không null
        if (newPlayer.getUsername() == null || newPlayer.getPassword() == null || newPlayer.getName() == null) {
            response.put("error", "Username, Password và Name không được để trống!");
            return ResponseEntity.badRequest().body(response);
        }

        // Kiểm tra username đã tồn tại
        if (playerRepository.findByUsername(newPlayer.getUsername()) != null) {
            response.put("error", "Username đã được sử dụng!");
            return ResponseEntity.badRequest().body(response);
        }

        // Kiểm tra độ dài của username, password và tên
        if (!isValidUsername(newPlayer.getUsername())) {
            response.put("error", "Username phải có độ dài từ 5 đến 20 ký tự.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!isValidPassword(newPlayer.getPassword())) {
            response.put("error", "Password phải có độ dài từ 6 đến 20 ký tự.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!isValidName(newPlayer.getName())) {
            response.put("error", "Tên phải có độ dài từ 3 đến 50 ký tự.");
            return ResponseEntity.badRequest().body(response);
        }

        // Thiết lập điểm ban đầu
        newPlayer.setTotal_point(0);
        newPlayer.setLives(4);

        // Lưu thông tin người chơi vào cơ sở dữ liệu
        playerRepository.save(newPlayer);

        response.put("message", "Tạo tài khoản thành công!");
        return ResponseEntity.ok(response);
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 5 && username.length() <= 20;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    private boolean isValidName(String name) {
        return name.length() >= 3 && name.length() <= 50;
    }
}