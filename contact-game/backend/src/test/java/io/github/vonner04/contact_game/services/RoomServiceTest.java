package io.github.vonner04.contact_game.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.vonner04.contact_game.domain.Player;
import io.github.vonner04.contact_game.domain.Room;
import io.github.vonner04.contact_game.dtos.CreateRoomRequest;
import io.github.vonner04.contact_game.dtos.CreateRoomResponse;
import io.github.vonner04.contact_game.repository.InMemoryRoomStore;

public class RoomServiceTest {
    private RoomService service;
    private InMemoryRoomStore store; 
    private RoomCodeGenerator generator;

    @BeforeEach
    void setUp(){
        store = new InMemoryRoomStore();
        generator = new RoomCodeGenerator();
        service = new RoomService(store, generator);
    }
   
    @Test
    @DisplayName("Should return a valid room response")
    void testCreateRoom(){
        String playerName = "test";
        CreateRoomRequest request = new CreateRoomRequest(playerName);
        CreateRoomResponse response = service.createRoom(request);
        
        assertAll("stored room", 
            () -> assertNotNull(response, "response should not be null"), 
            () -> assertNotNull(response.roomCode(), "Room code should exist"),
            () -> assertNotNull(response.roomID(), "Room ID should exist"),
            () -> assertNotNull(response.playerID(), "Host player ID should exist")
        );

        Optional<Room> storedRoom = store.findById(response.roomID());

        assertTrue(storedRoom.isPresent());

        Room room = storedRoom.get();

        assertAll("stored room",
            () -> assertEquals(response.roomID(), room.getId(), "Stored room ID should match response"),
            () -> assertEquals(response.roomCode(), room.getRoomCode(), "Stored room code should match response"),
            () -> assertEquals(response.playerID(), room.getHostPlayerId(), "Stored host ID should match response"),
            () -> assertEquals(1, room.getPlayers().size(), "Room should contain exactly one player"),
            () -> assertNotNull(room.getSettings(), "Room should have default settings")
        );

        Player host = room.getPlayers().get(0);
        assertAll("host player",
            () -> assertEquals(playerName, host.getName(), "Host name should match request"),
            () -> assertEquals(response.playerID(), host.getId(), "Host ID should match response")
        );

    }
    
    

    /**
     * TODO
     * Test joinRoom method
     * joining with valid roomCode succeeds
     * joining player gets a playerId
     * room now has 2 players
     * host remains unchanged
     * joining player is not host
     * joining with invalid roomCode fails
     * joining full room fails
     * joining started room fails
     */
}
