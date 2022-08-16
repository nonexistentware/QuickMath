package com.nonexistentware.quickmath.Model;

public class PlayerModel {
    String duelWin;
    String playerLevel;
    String playerScore;
    String Defeated;
    String playerEmojiAchieve;
    String timeAttackTimePlay;
    String totalTimePlay;
    String classicTimePlay;
    String hardLevelSelect;

    public PlayerModel() {
    }

    public PlayerModel(String duel_win, String player_level, String player_score) {
        this.duelWin = duel_win;
        this.playerLevel = player_level;
        this.playerScore = player_score;
    }

    public String getDuelWin() {
        return duelWin;
    }

    public void setDuelWin(String duelWin) {
        this.duelWin = duelWin;
    }

    public String getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(String playerLevel) {
        this.playerLevel = playerLevel;
    }

    public String getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(String playerScore) {
        this.playerScore = playerScore;
    }
}
