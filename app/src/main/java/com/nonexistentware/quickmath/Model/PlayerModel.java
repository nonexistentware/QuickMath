package com.nonexistentware.quickmath.Model;

public class PlayerModel {
    String duel_win;
    String player_level;
    String player_score;

    public PlayerModel() {
    }

    public PlayerModel(String duel_win, String player_level, String player_score) {
        this.duel_win = duel_win;
        this.player_level = player_level;
        this.player_score = player_score;
    }

    public String getDuel_win() {
        return duel_win;
    }

    public void setDuel_win(String duel_win) {
        this.duel_win = duel_win;
    }

    public String getPlayer_level() {
        return player_level;
    }

    public void setPlayer_level(String player_level) {
        this.player_level = player_level;
    }

    public String getPlayer_score() {
        return player_score;
    }

    public void setPlayer_score(String player_score) {
        this.player_score = player_score;
    }
}
