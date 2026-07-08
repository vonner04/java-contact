package io.github.vonner04.contact_game.domain;

import java.util.List;

public class Room {
    private final String id;
    private final String roomCode;
    private Player host;
    private List<Player> players;
    private String hostPlayerId;
    private String gameMasterId;
    private RoomSettings settings;
    private RoomState state;
    
    public Room(String id, String roomCode, String hostPlayerId, List<Player> players, RoomSettings settings) {
        this.id = id;
        this. roomCode = roomCode;
        this.hostPlayerId = hostPlayerId;
        this.players = players;
        this.settings = settings;
    }
    
    public String getId() {
        return id;
    }
    public String getRoomCode() {
        return roomCode;
    }
    public Player getHost() {
        return host;
    }
    public void setHost(Player host) {
        this.host = host;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public String getHostPlayerId() {
        return hostPlayerId;
    }
    public void setHostPlayerId(String hostPlayerId) {
        this.hostPlayerId = hostPlayerId;
    }
    public String getGameMasterId() {
        return gameMasterId;
    }
    public void setGameMasterId(String gameMasterId) {
        this.gameMasterId = gameMasterId;
    }
    public RoomSettings getSettings() {
        return settings;
    }
    public void setSettings(RoomSettings settings) {
        this.settings = settings;
    }
    public RoomState getState() {
        return state;
    }
    public void setState(RoomState state) {
        this.state = state;
    }
}
