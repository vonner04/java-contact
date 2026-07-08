package io.github.vonner04.contact_game.domain;
public class Player {
    private String id;
    private String name;
    private LobbyRole lobbyRole;
    private GameRole gameRole;
    private PlayerStatus status;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LobbyRole getLobbyRole() {
        return lobbyRole;
    }
    public void setLobbyRole(LobbyRole lobbyRole) {
        this.lobbyRole = lobbyRole;
    }
    public GameRole getGameRole() {
        return gameRole;
    }
    public void setGameRole(GameRole gameRole) {
        this.gameRole = gameRole;
    }
    public PlayerStatus getStatus() {
        return status;
    }
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

}
