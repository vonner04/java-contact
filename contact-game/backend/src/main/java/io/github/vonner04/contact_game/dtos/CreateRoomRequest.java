package io.github.vonner04.contact_game.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * CreateRoomRequest
 * 
 * @param playerName any letters or digits
 */
public record CreateRoomRequest(
        @NotBlank @Size(min = 1, max = 20) String playerName) {
}
