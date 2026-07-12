package io.github.vonner04.contact_game.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.vonner04.contact_game.domain.LobbyRole;
import io.github.vonner04.contact_game.domain.Player;
import io.github.vonner04.contact_game.domain.Room;
import io.github.vonner04.contact_game.domain.RoomSettings;

public class InMemoryRoomStoreTest {
    private InMemoryRoomStore store;

    @BeforeEach
    void setUp() {
        store = new InMemoryRoomStore();
    }

    @AfterEach
    void cleanUp() {
        store = null;
    }

    @Test
    @DisplayName("Save and find: should return saved room by room ID")
    void testSaveAndFind() {
        Room room = createTestRoom("room-1", "ABCD1234", "host-1");

        store.save(room);

        Optional<Room> foundRoom = store.findById("room-1");

        assertTrue(foundRoom.isPresent(), "Saved room should be found by ID");
        assertEquals(room, foundRoom.get(), "Found room should match saved room");
    }

    @Test
    @DisplayName("Find by ID should return empty when room does not exist")
    void testFindByIdNoRoom() {

        Optional<Room> room = store.findById("missing-room");

        assertTrue(room.isEmpty());
    }

    @Test
    @DisplayName("Exists by ID should return false when the room doesn't exist")
    void testExistsByIdNoRoom() {
        assertFalse(store.existsById("missing-room"), "Store should report missing room as not existing");
    }

    @Test
    @DisplayName("Exists by ID should return true when the room exists")
    void testExistsByIdRoomExists() {
        Room room = createTestRoom("room-1", "ABCD1234", "host-1");

        store.save(room);

        assertTrue(store.existsById("room-1"), "Store should report saved room as existing");
        assertFalse(store.existsById("missing-room"), "Deleting a nonexisting room should safely do nothing");
    }

    @Test
    @DisplayName("Delete by ID: should remove saved room")
    void testDeleteByIdRoomExists() {
        Room room = createTestRoom("room-1", "ABCD1234", "host-1");
        store.save(room);

        store.deleteById("room-1");

        assertFalse(store.existsById("room-1"), "Deleted room should not exist");
        assertTrue(store.findById("room-1").isEmpty(), "Deleted room should not be found");
    }

    @Test
    @DisplayName("Find all: should return all saved rooms")
    void testFindAllSavedRooms() {
        Room roomOne = createTestRoom("room-1", "ABCD1234", "host-1");
        Room roomTwo = createTestRoom("room-2", "WXYZ5678", "host-2");

        store.save(roomOne);
        store.save(roomTwo);

        assertEquals(2, store.findAll().size(), "Store should contain two rooms");
        assertTrue(store.findAll().contains(roomOne), "Store should contain first room");
        assertTrue(store.findAll().contains(roomTwo), "Store should contain second room");

    }

    @Test
    @DisplayName("Find by host player ID: should return room hosted by player")
    void testFindRoomByHostPlayerId() {
        Room room = createTestRoom("room-1", "ABCD1234", "host-1");

        store.save(room);

        Optional<Room> foundRoom = store.findRoomByHostPlayerId("host-1");

        assertTrue(foundRoom.isPresent(), "Room hosted by player should be found");
        assertEquals(room, foundRoom.get(), "Found room should match hosted room");
    }

    @Test
    @DisplayName("Find by host player ID: should return empty when host has no room")
    void testFindRoomByHostPlayerIdNoRoom() {
        Optional<Room> foundRoom = store.findRoomByHostPlayerId("missing-host");

        assertTrue(foundRoom.isEmpty(), "Missing host should return empty");
    }

    @Test
    @DisplayName("Find by roomCode: should return a room with the given room code")
    void testFindByRoomCodeRoomExists() {
        Room room = createTestRoom("room-1", "ABCD1234", "host-1");

        store.save(room);

        Optional<Room> foundRoom = store.findByRoomCode("ABCD1234");

        assertTrue(foundRoom.isPresent(), "A room should be found with the given room code");
        assertEquals(foundRoom.get(), room, "Found room should match created room");
    }

    @Test
    @DisplayName("Find by roomCode: should return empty when there is no room code")
    void testFindByRoomCodeNoRoom() {
        Optional<Room> room = store.findByRoomCode("ABCD1234");

        assertTrue(room.isEmpty(), "Missing room code should return empty");
    }

    @Test
    @DisplayName("Exists by room code: should return true when there is an existing room with the given room code")
    void testRoomCodeExists() {
        Room room = createTestRoom("room-1", "ABCD1234", "host-1");

        store.save(room);

        assertTrue(store.existsByRoomCode("ABCD1234"), "Store should report saved room with roomCode");
        assertFalse(store.existsByRoomCode("missing"),
                "Store should report no saved rooms with non-existent room code");
    }

    private Room createTestRoom(String roomID, String roomCode, String hostPlayerID) {
        Player host = new Player(hostPlayerID, "Host", LobbyRole.HOST);
        RoomSettings settings = new RoomSettings();

        return new Room(roomID, roomCode, host, hostPlayerID, List.of(host), settings);
    }

}
