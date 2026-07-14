package io.github.vonner04.contact_game.dtos;

import jakarta.validation.constraints.NotBlank;

//Not sure if I should add list of players so that frontend updates...
/**
 * 
 * JoinRoomResponse
 * 
 * @param roomID   UUID string
 * @param playerID UUID string
 */
public record JoinRoomResponse(
        @NotBlank String roomID,
        @NotBlank String playerID) {
}
