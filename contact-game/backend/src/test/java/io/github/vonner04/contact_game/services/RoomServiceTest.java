package io.github.vonner04.contact_game.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.vonner04.contact_game.domain.Player;
import io.github.vonner04.contact_game.domain.Room;
import io.github.vonner04.contact_game.dtos.CreateRoomRequest;
import io.github.vonner04.contact_game.dtos.CreateRoomResponse;
import io.github.vonner04.contact_game.dtos.JoinRoomRequest;
import io.github.vonner04.contact_game.dtos.JoinRoomResponse;
import io.github.vonner04.contact_game.exceptions.RoomFullException;
import io.github.vonner04.contact_game.exceptions.RoomNotFoundException;
import io.github.vonner04.contact_game.repository.InMemoryRoomStore;

public class RoomServiceTest {
        private RoomService service;
        private InMemoryRoomStore store;
        private RoomCodeGenerator generator;

        @BeforeEach
        void setUp() {
                store = new InMemoryRoomStore();
                generator = new RoomCodeGenerator();
                service = new RoomService(store, generator);
        }

        @Test
        @DisplayName("Should return a valid room response")
        void testCreateRoom() {
                String playerName = "test";
                CreateRoomRequest request = new CreateRoomRequest(playerName);
                CreateRoomResponse response = service.createRoom(request);

                assertAll("response validation",
                                () -> assertNotNull(response, "response should not be null"),
                                () -> assertNotNull(response.roomCode(), "Room code should exist"),
                                () -> assertNotNull(response.roomID(), "Room ID should exist"),
                                () -> assertNotNull(response.playerID(), "Host player ID should exist"));

                Optional<Room> storedRoom = store.findById(response.roomID());

                assertTrue(storedRoom.isPresent());

                Room room = storedRoom.get();

                assertAll("stored room validations",
                                () -> assertEquals(response.roomID(), room.getId(),
                                                "Stored room ID should match response"),
                                () -> assertEquals(response.roomCode(), room.getRoomCode(),
                                                "Stored room code should match response"),
                                () -> assertEquals(response.playerID(), room.getHostPlayerId(),
                                                "Stored host ID should match response"),
                                () -> assertEquals(1, room.getPlayers().size(),
                                                "Room should contain exactly one player"),
                                () -> assertNotNull(room.getSettings(), "Room should have default settings"));

                Player host = room.getPlayers().get(0);
                assertAll("host player validations",
                                () -> assertEquals(playerName, host.getName(), "Host name should match request"),
                                () -> assertEquals(response.playerID(), host.getId(), "Host ID should match response"));

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
         */
        @Test
        @DisplayName("Should return a valid join room response")
        void testJoinRoom() {
                // Create sample room
                CreateRoomRequest createRequest = new CreateRoomRequest("Host");
                CreateRoomResponse createResponse = service.createRoom(createRequest);

                String roomCode = createResponse.roomCode();

                JoinRoomRequest joinRequest = new JoinRoomRequest(roomCode, "John Doe");
                JoinRoomResponse joinResponse = service.joinRoom(joinRequest);

                assertAll("join room response validaitons",
                                () -> assertNotNull(joinResponse, "response should not be null"),
                                () -> assertNotNull(joinResponse.roomID(), "Room ID should exist"),
                                () -> assertNotNull(joinResponse.playerID(), "New player ID should exist"));

                Optional<Room> storedRoom = store.findById(createResponse.roomID());

                assertTrue(storedRoom.isPresent());

                Room room = storedRoom.get();

                assertAll("stored room validations",
                                () -> assertEquals(2, room.getPlayers().size(), "The number of players should be 2"),
                                () -> assertEquals(createResponse.playerID(), room.getHost().getId(),
                                                "The host player ID should match and remain unchanged"),
                                () -> assertNotEquals(room.getHost().getId(), joinResponse.playerID(),
                                                "The new player must not be the host"));

                Player joinedPlayer = room.getPlayers().stream()
                                .filter(player -> player.getId().equals(joinResponse.playerID()))
                                .findFirst()
                                .orElseThrow();

                assertAll("joined player validations",
                                () -> assertEquals("John Doe", joinedPlayer.getName(),
                                                "The joined player's name should match the request"),
                                () -> assertNotEquals(createResponse.playerID(), joinedPlayer.getId(),
                                                "The joined player should have a unique ID"),
                                () -> assertTrue(room.getPlayers().contains(room.getHost()),
                                                "The host should remain in the room"));
        }

        @Test
        @DisplayName("Should reject joining a room that does not exist")
        void testJoinInvalidRoom() {
                JoinRoomRequest request = new JoinRoomRequest("INVALID1", "John Doe");

                assertThrows(RoomNotFoundException.class,
                                () -> service.joinRoom(request));
        }

        @Test
        @DisplayName("Should reject joining a room that is full")
        void testJoinFullRoom() {
                // Create sample room
                CreateRoomRequest createRequest = new CreateRoomRequest("Host");
                CreateRoomResponse createResponse = service.createRoom(createRequest);

                populateRoomToMax(createResponse);

                JoinRoomRequest extraPlayerRequest = new JoinRoomRequest(createResponse.roomCode(), "EXTRA");

                assertThrows(RoomFullException.class, () -> service.joinRoom(extraPlayerRequest));

                Room storedRoom = store.findById(createResponse.roomID())
                                .orElseThrow();

                assertEquals(
                                storedRoom.getSettings().getMaxPlayers(),
                                storedRoom.getPlayers().size(),
                                "A failed join attempt should not add another player");
        }

        private void populateRoomToMax(CreateRoomResponse createResponse) {
                String roomCode = createResponse.roomCode();

                Room room = store.findById(createResponse.roomID())
                                .orElseThrow(() -> new AssertionError("Created room should exist"));

                int maxPlayers = room.getSettings().getMaxPlayers();
                int currentPlayers = room.getPlayers().size();

                for (int i = currentPlayers; i < maxPlayers; i++) {
                        JoinRoomRequest request = new JoinRoomRequest(roomCode, "Player " + i);
                        service.joinRoom(request);
                }
        }
}
