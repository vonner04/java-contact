package io.github.vonner04.contact_game.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 
 * JoinRoomRequest
 * 
 * @param roomCode   upper, alphanumeric of length 8
 * @param playerName any letters or digits
 */
public record JoinRoomRequest(
        @NotBlank @Size(min = 8, max = 8) String roomCode,
        @NotBlank @Size(min = 2, max = 20) String playerName) {
}
