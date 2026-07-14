package io.github.vonner04.contact_game.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 
 * CreateRoomResponse
 * 
 * @param roomID   UUID string
 * @param roomCode upper, alphanum of length 8
 * @param playerID UUID string
 */
public record CreateRoomResponse(
        @NotBlank String roomID,
        @NotBlank @Size(min = 8, max = 8) String roomCode,
        @NotBlank String playerID) {
}
