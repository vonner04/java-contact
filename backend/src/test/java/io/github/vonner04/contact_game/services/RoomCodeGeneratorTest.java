package io.github.vonner04.contact_game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoomCodeGeneratorTest {

    private RoomCodeGenerator generator;

    @BeforeEach
    void setup() {
         generator = new RoomCodeGenerator();
    }

    @AfterEach
    void cleanUp() {
        generator = null;
    }

    //I could probably combine both test case by doing .matches("[A-Z0-9]{8}") but whatever
    @Test
    @DisplayName("Should return only alphanumeric characters, [A-Z; 0-9]")
    void testAlphaNum(){
        String code = generator.generateRoomCode();
        assertNotNull(code);
        assertTrue(code.matches("[A-Z0-9]+"));
    }

    @Test
    @DisplayName("Should return room code with length of 8")
    void testShouldReturnLengthOfEight(){
        String code = generator.generateRoomCode();

        assertNotNull(code);
        assertEquals(8, code.length(), "code length should be 8");
    }

}
