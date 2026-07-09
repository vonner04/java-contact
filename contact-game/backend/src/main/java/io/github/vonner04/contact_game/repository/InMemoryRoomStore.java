package io.github.vonner04.contact_game.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import io.github.vonner04.contact_game.domain.Room;

@Repository
public class InMemoryRoomStore implements RoomStore {

    private final Map<String, Room> activeRooms = new ConcurrentHashMap<>();

    @Override
    public void save(Room item) {
        activeRooms.put(item.getId(), item);
    }

    @Override
    public Optional<Room> findById(String id) {
        return Optional.ofNullable(activeRooms.get(id));
    }

    @Override
    public void deleteById(String id) {
        activeRooms.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return activeRooms.containsKey(id);
    }

    @Override
    public Collection<Room> findAll() {
       return activeRooms.values();
    }
    
    @Override
    public Collection<Room> findExpiredRooms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findExpiredRooms'");
    }

    @Override
    public Optional<Room> findRoomByHostPlayerId(String hostPlayerID) {
        return activeRooms.values().stream().filter(room -> room.getHostPlayerId().equals(hostPlayerID)).findFirst();
    }
    
}
