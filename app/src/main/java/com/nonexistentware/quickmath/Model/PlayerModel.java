package com.nonexistentware.quickmath.Model;

public class PlayerModel {
    long duelWin;
   public long playerLevel;
    long playerScore;
    String playerName;
    String defeated;
    String playerEmojiAchieve;
    String timeAttackTimePlay;
    String totalTimePlay;
    String classicTimePlay;
    String difficultLevel;
    String remainCounterTime;
    String remainCounterTimeTemp;
    String wrongAnswersCounter;
    String correctAnswersCounter;
    String gameMode;
    String playerFlag; //mark for be visible in top ranked player list
    String playerImg;
    long totalPlayedGamesCounter;
    long attemptsToStartTheGame;

    public PlayerModel() {
    }

    public PlayerModel(long duelWin, long playerLevel, long playerScore, String playerName, String defeated, String playerEmojiAchieve, String timeAttackTimePlay, String totalTimePlay, String classicTimePlay, String difficultLevel, String remainCounterTime,
                       String remainCounterTimeTemp, String correctAnswersCounter, String wrongAnswersCounter, long totalPlayedGamesCounter, long attemptsToStartTheGame,
                       String gameMode, String playerFlag, String playerImg) {
        this.duelWin = duelWin;
        this.playerLevel = playerLevel;
        this.playerScore = playerScore;
        this.playerName = playerName;
        this.defeated = defeated;
        this.playerEmojiAchieve = playerEmojiAchieve;
        this.timeAttackTimePlay = timeAttackTimePlay;
        this.totalTimePlay = totalTimePlay;
        this.classicTimePlay = classicTimePlay;
        this.difficultLevel = difficultLevel;
        this.remainCounterTime = remainCounterTime;
        this.remainCounterTimeTemp = remainCounterTimeTemp;
        this.correctAnswersCounter = correctAnswersCounter;
        this.wrongAnswersCounter = wrongAnswersCounter;
        this.totalPlayedGamesCounter = totalPlayedGamesCounter;
        this.attemptsToStartTheGame = attemptsToStartTheGame;
        this.gameMode = gameMode;
        this.playerFlag = playerFlag;
        this.playerImg = playerImg;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setTotalPlayedGamesCounter(long totalPlayedGamesCounter) {
        this.totalPlayedGamesCounter = totalPlayedGamesCounter;
    }

    public String getDefeated() {
        return defeated;
    }

    public void setDefeated(String defeated) {
        defeated = defeated;
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

    public String getWrongAnswersCounter() {
        return wrongAnswersCounter;
    }

    public void setWrongAnswersCounter(String wrongAnswersCounter) {
        this.wrongAnswersCounter = wrongAnswersCounter;
    }

    public String getCorrectAnswersCounter() {
        return correctAnswersCounter;
    }

    public void setCorrectAnswersCounter(String correctAnswersCounter) {
        this.correctAnswersCounter = correctAnswersCounter;
    }

    public long getTotalPlayedGameAsCounter() {
        return totalPlayedGamesCounter;
    }

    public long getTotalPlayedGamesCounter() {
        return totalPlayedGamesCounter;
    }



    public long getAttemptsToStartTheGame() {
        return attemptsToStartTheGame;
    }

    public void setAttemptsToStartTheGame(long attemptsToStartTheGame) {
        this.attemptsToStartTheGame = attemptsToStartTheGame;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getPlayerFlag() {
        return playerFlag;
    }

    public void setPlayerFlag(String playerFlag) {
        this.playerFlag = playerFlag;
    }

    public String getPlayerImg() {
        return playerImg;
    }

    public void setPlayerImg(String playerImg) {
        this.playerImg = playerImg;
    }
}
