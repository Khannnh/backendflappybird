package com.example.flappybird.model;

import java.time.LocalDateTime;

public class GameOverRequest {
    private int player_id; // ID của người chơi
    private int score;    // Điểm số
    private LocalDateTime play_date;
    
	public int getPlayer_id() {
		return player_id;
	}

	public LocalDateTime getPlay_date() {
		return play_date;
	}

	public void setPlay_date(LocalDateTime play_date) {
		this.play_date = play_date;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}


}