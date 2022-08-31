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
    String remainCounterTime;

    public PlayerModel() {
    }

    public PlayerModel(String duelWin, String playerLevel, String playerScore, String defeated, String playerEmojiAchieve, String timeAttackTimePlay, String totalTimePlay, String classicTimePlay, String hardLevelSelect, String remainCounterTime) {
        this.duelWin = duelWin;
        this.playerLevel = playerLevel;
        this.playerScore = playerScore;
        Defeated = defeated;
        this.playerEmojiAchieve = playerEmojiAchieve;
        this.timeAttackTimePlay = timeAttackTimePlay;
        this.totalTimePlay = totalTimePlay;
        this.classicTimePlay = classicTimePlay;
        this.hardLevelSelect = hardLevelSelect;
        this.remainCounterTime = remainCounterTime;
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

    public String getDefeated() {
        return Defeated;
    }

    public void setDefeated(String defeated) {
        Defeated = defeated;
    }

    public String getPlayerEmojiAchieve() {
        return playerEmojiAchieve;
    }

    public void setPlayerEmojiAchieve(String playerEmojiAchieve) {
        this.playerEmojiAchieve = playerEmojiAchieve;
    }

    public String getTimeAttackTimePlay() {
        return timeAttackTimePlay;
    }

    public void setTimeAttackTimePlay(String timeAttackTimePlay) {
        this.timeAttackTimePlay = timeAttackTimePlay;
    }

    public String getTotalTimePlay() {
        return totalTimePlay;
    }

    public void setTotalTimePlay(String totalTimePlay) {
        this.totalTimePlay = totalTimePlay;
    }

    public String getClassicTimePlay() {
        return classicTimePlay;
    }

    public void setClassicTimePlay(String classicTimePlay) {
        this.classicTimePlay = classicTimePlay;
    }

    public String getHardLevelSelect() {
        return hardLevelSelect;
    }

    public void setHardLevelSelect(String hardLevelSelect) {
        this.hardLevelSelect = hardLevelSelect;
    }

    public String getRemainCounterTime() {
        return remainCounterTime;
    }

    public void setRemainCounterTime(String remainCounterTime) {
        this.remainCounterTime = remainCounterTime;
    }
}
