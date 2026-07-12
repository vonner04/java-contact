package io.github.vonner04.contact_game.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.vonner04.contact_game.domain.LobbyRole;
import io.github.vonner04.contact_game.domain.Player;
import io.github.vonner04.contact_game.domain.Room;
import io.github.vonner04.contact_game.domain.RoomSettings;
import io.github.vonner04.contact_game.domain.RoomState;
import io.github.vonner04.contact_game.dtos.CreateRoomRequest;
import io.github.vonner04.contact_game.dtos.CreateRoomResponse;
import io.github.vonner04.contact_game.dtos.JoinRoomRequest;
import io.github.vonner04.contact_game.dtos.JoinRoomResponse;
import io.github.vonner04.contact_game.exceptions.RoomFullException;
import io.github.vonner04.contact_game.exceptions.RoomNotFoundException;
import io.github.vonner04.contact_game.repository.RoomStore;

/**
 * Handles room-related application actions.
 * 
 * <p>
 * Coordinates room creation by generating room and playerIDs, creating the host
 * player,
 * assigning default room settings, generating a public room code, and saving
 * the room
 * through {@link RoomStore}.
 */
@Service
public class RoomService {
    private final RoomStore store;
    private final RoomCodeGenerator codeGenerator;

    public RoomService(RoomStore store, RoomCodeGenerator codeGenerator) {
        this.store = store;
        this.codeGenerator = codeGenerator;
    }

    /**
     * Creates a new room and adds the requesting player as the host.
     * 
     * <p>
     * The room and host/first-player recieves an internal UUID-based ID and
     * generates a public upper alphanumeric room code that can be shared with
     * other players.
     * 
     * @param request the room creation request containing player name and ID
     * @return response containing the created room ID, room code and host player
     *         ID.
     */
    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        String roomId = UUID.randomUUID().toString();
        String hostPlayerId = UUID.randomUUID().toString();

        Player host = new Player(
                hostPlayerId,
                request.playerName(),
                LobbyRole.HOST);

        RoomSettings defaultSettings = new RoomSettings();

        String roomCode = codeGenerator.generateRoomCode();

        Room room = new Room(roomId, roomCode, host, hostPlayerId, new ArrayList<Player>(List.of(host)),
                defaultSettings);
        room.setState(RoomState.LOBBY);

        store.save(room);

        return new CreateRoomResponse(
                roomId,
                roomCode,
                hostPlayerId);
    }

    /**
     * Creates a new player and adds them into an existing room.
     * 
     * <p>
     * The new player utilises the public room code to join to
     * the existing room
     * 
     * @param request the join request containing room code and player name
     * @return A response containing the room id and new player's id if a room is
     *         found or else throw an IllegalArgumentException
     */
    public JoinRoomResponse joinRoom(JoinRoomRequest request) {

        Room room = findByRoomCodeOrThrow(request.roomCode());

        String newPlayerId = UUID.randomUUID().toString();

        Player player = new Player(newPlayerId, request.playerName(), LobbyRole.PLAYER);

        room.addPlayer(player);

        store.save(room);

        return new JoinRoomResponse(room.getId(), newPlayerId);
    }

    /**
     * Helper method to find room code and throw an appropriate exception
     * 
     * @param roomCode the public upper, alphanumeric room code to join a lobby
     * @return the room entity used to update in the parent method.
     */
    private Room findByRoomCodeOrThrow(String roomCode) {
        return store.findByRoomCode(roomCode)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }
}
