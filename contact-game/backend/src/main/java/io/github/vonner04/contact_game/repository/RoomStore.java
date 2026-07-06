package io.github.vonner04.contact_game.repository;

import java.util.Collection;
import java.util.Optional;

import io.github.vonner04.contact_game.domain.Room;

/**
 * Storage abstraction for active {@link Room} objects.
 */
public interface RoomStore extends Store<Room, String> {

    /**
     * TODO: designing for expired rooms 
     * 1) StoredRoom class contains Room object and timestamp metadata?
     * 2) Emitting Events?
     */

    /**
     * Finds rooms that are considered expired:
     * 
     * Conditions:
     * 1) A room with no connected players
     * 2) A room that has been inactive for too long (No status changes after 30mins)
     * 3) A room past its allowed lifetime of (5hours)
     * 
     * @return a collection of rooms that should be removed from active storage
     */
    Collection<Room> findExpiredRooms();

    Optional<Room> findRoomByHostPlayerID(String hostPlayerID);
}
