package com.example.flappybird.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gamesession")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //mqh giữa 2 thực thể 
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "play_date", nullable = false)
    private LocalDateTime playDate;

    @Column(nullable = false)
    private int score;

    // Constructors
    public GameSession() {
        this.playDate = LocalDateTime.now(); // Set default play date to now
    }
    
    //constructor tạo mới đối tượng GameSession gồm đối tượng player và score 
    public GameSession(Player player, int score) {
        this.player = player;
        this.score = score;
        this.playDate = LocalDateTime.now(); // Set default play date to now
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDateTime getPlayDate() {
        return playDate;
    }

    public void setPlayDate(LocalDateTime playDate) {
        this.playDate = playDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}