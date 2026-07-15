package io.github.vonner04.contact_game.services;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

/**
 * Generates uppercase alphanumeric room code of length {@value #CODE_LENGTH}
 */
@Component
public class RoomCodeGenerator {
    private static final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String UPPER_ALPHANUM = UPPER_LETTERS + DIGITS;
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a random code using uppercase letters and digits.
     * @return a randomly generated uppercase alphanumeric room code 
     * with a length of {@value #CODE_LENGTH}
     */
    public String generateRoomCode() {
     StringBuilder roomCode = new StringBuilder();
     
     /**
      * Utilise SecureRandom to generate a random integer
      * used to therefore fetch a random upper alphanumeric character.
      */
     for (int i = 0; i < CODE_LENGTH; i++) {
        int randomIndex = RANDOM.nextInt(UPPER_ALPHANUM.length());
        char randomCharacter = UPPER_ALPHANUM.charAt(randomIndex);
        roomCode.append(randomCharacter);
     }
     return roomCode.toString();
    }
}
