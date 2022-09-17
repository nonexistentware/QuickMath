package com.nonexistentware.quickmath.Model;

public class PlayerModel {
    Long duelWin;
    Long playerLevel;
    Long playerScore;
    String Defeated;
    String playerEmojiAchieve;
    String timeAttackTimePlay;
    String totalTimePlay;
    String classicTimePlay;
    String difficultLevel;
    String remainCounterTime;
    String remainCounterTimeTemp;

    public PlayerModel() {
    }

    public PlayerModel(Long duelWin, long playerLevel, long playerScore, String defeated, String playerEmojiAchieve, String timeAttackTimePlay, String totalTimePlay, String classicTimePlay, String difficultLevel, String remainCounterTime, String remainCounterTimeTemp) {
        this.duelWin = duelWin;
        this.playerLevel = playerLevel;
        this.playerScore = playerScore;
        Defeated = defeated;
        this.playerEmojiAchieve = playerEmojiAchieve;
        this.timeAttackTimePlay = timeAttackTimePlay;
        this.totalTimePlay = totalTimePlay;
        this.classicTimePlay = classicTimePlay;
        this.difficultLevel = difficultLevel;
        this.remainCounterTime = remainCounterTime;
        this.remainCounterTimeTemp = remainCounterTimeTemp;
    }

    public long getDuelWin() {
        return duelWin;
    }

    public void setDuelWin(long duelWin) {
        this.duelWin = duelWin;
    }

    public long getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(long playerLevel) {
        this.playerLevel = playerLevel;
    }

    public long getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(long playerScore) {
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

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(String difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public String getRemainCounterTime() {
        return remainCounterTime;
    }

    public void setRemainCounterTime(String remainCounterTime) {
        this.remainCounterTime = remainCounterTime;
    }

    public String getRemainCounterTimeTemp() {
        return remainCounterTimeTemp;
    }

    public void setRemainCounterTimeTemp(String remainCounterTimeTemp) {
        this.remainCounterTimeTemp = remainCounterTimeTemp;
    }
}
