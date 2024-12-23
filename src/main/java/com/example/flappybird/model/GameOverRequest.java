package com.example.flappybird.model;

public class GameOverRequest {
    private int playerId; // ID của người chơi
    private int score;    // Điểm số

    // Getters và Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}