package io.github.vonner04.contact_game.domain;
public class RoomSettings {
    private int maxPlayers;
    private int minWordLength;
    private int maxWordLength;
    private int answerTimer;
    private boolean showProgression;

    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public void setMinWordLength(int minWordLength) {
        this.minWordLength = minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public void setMaxWordLength(int maxWordLength) {
        this.maxWordLength = maxWordLength;
    }

    public int getAnswerTimer() {
        return answerTimer;
    }

    public void setAnswerTimer(int answerTimer) {
        this.answerTimer = answerTimer;
    }

    public boolean isShowProgression() {
        return showProgression;
    }

    public void setShowProgression(boolean showProgression) {
        this.showProgression = showProgression;
    }
}
