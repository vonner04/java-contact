package io.github.vonner04.contact_game.domain;

/**
 * Represents configurable settings for a room.
 *
 * <p>A new {@code RoomSettings} object starts with default values suitable for a
 * standard game room. 
 */
public class RoomSettings {
    private int maxPlayers;
    private int minWordLength;
    private int maxWordLength;
    private int answerTimerSeconds;
    private boolean showProgression;

    
    private static final int DEFAULT_MIN_WORD_LENGTH = 3;
    private static final int DEFAULT_PLAYER_COUNT = 8;
    private static final int DEFAULT_MAX_WORD_LENGTH = 8;
    private static final int DEFAULT_ANSWER_TIMER = 10; // in seconds
    private static final boolean DEFAULT_SHOW_PROGRESSION = true;

    public RoomSettings() {
        this.maxPlayers = DEFAULT_PLAYER_COUNT;
        this.minWordLength = DEFAULT_MIN_WORD_LENGTH;
        this.maxWordLength = DEFAULT_MAX_WORD_LENGTH;
        this.answerTimerSeconds = DEFAULT_ANSWER_TIMER;
        this.showProgression = DEFAULT_SHOW_PROGRESSION;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    /**
     * Applicable values are within 3-10
     * @param maxPlayers the maximum number of player allowed
     */
    public void setMaxPlayers(int maxPlayers) {
        if (maxPlayers < 3) return;
        if (maxPlayers > 10) return;
        this.maxPlayers = maxPlayers;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    /**
     * Values below 3 are ignored
     * @param minWordLength the minimum allowed word length
     */
    public void setMinWordLength(int minWordLength) {
        if (minWordLength < 3) return;
        this.minWordLength = minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    /**
     * Values above 20 are ignored as the game may take very long
     * @param maxWordLength the maximum allowed word length
     */
    public void setMaxWordLength(int maxWordLength) {
        if (maxWordLength > 20) return;
        this.maxWordLength = maxWordLength;
    }

    public int getAnswerTimerSeconds() {
        return answerTimerSeconds;
    }

    /**
     * Values between 5 and 30 are accepted as it seems to be
     * a reasonable answer time
     * @param timer the answer time limit in seconds
     */
    public void setAnswerTimerSeconds(int timer) {
        if (timer < 5 || timer > 30) return; // 5 - 30
        this.answerTimerSeconds = timer;
    }

    public boolean isShowProgression() {
        return showProgression;
    }

    public void setShowProgression(boolean showProgression) {
        this.showProgression = showProgression;
    }
}
