package io.github.vonner04.contact_game.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.vonner04.contact_game.dtos.CreateRoomRequest;
import io.github.vonner04.contact_game.dtos.CreateRoomResponse;
import io.github.vonner04.contact_game.dtos.JoinRoomRequest;
import io.github.vonner04.contact_game.dtos.JoinRoomResponse;
import io.github.vonner04.contact_game.exceptions.RoomFullException;
import io.github.vonner04.contact_game.exceptions.RoomNotFoundException;
import io.github.vonner04.contact_game.services.RoomService;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService service;

    @Test
    @DisplayName("Create room should return 201 Created with room response")
    void testCreateRoomSuccess() throws Exception {
        // Fake response return when calling createRoom()
        CreateRoomResponse response = new CreateRoomResponse("room-123", "ABCD1234", "player-123");

        when(service.createRoom(any(CreateRoomRequest.class))).thenReturn(response);

        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "playerName": "Joel"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomID").value("room-123"))
                .andExpect(jsonPath("$.roomCode").value("ABCD1234"))
                .andExpect(jsonPath("$.playerID").value("player-123"));

        // Verify that the controller passed the correct values to the service
        ArgumentCaptor<CreateRoomRequest> captor = ArgumentCaptor.forClass(CreateRoomRequest.class);
        verify(service).createRoom(captor.capture());

        CreateRoomRequest capturedRequest = captor.getValue();

        assertEquals("Joel", capturedRequest.playerName());
    }

    @Test
    @DisplayName("Should join an existing Room")
    void testJoinExistingRoom() throws Exception {
        JoinRoomResponse response = new JoinRoomResponse("room-id", "player-id");
        when(service.joinRoom(any(JoinRoomRequest.class))).thenReturn(response);

        mockMvc.perform(post("/rooms/ABCD1234/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "playerName": "John Doe",
                            "roomCode": "ABCD1234"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerID").value("player-id"))
                .andExpect(jsonPath("$.roomID").value("room-id"));

        // Verify that the controller passed the correct values to the service
        ArgumentCaptor<JoinRoomRequest> captor = ArgumentCaptor.forClass(JoinRoomRequest.class);
        verify(service).joinRoom(captor.capture());

        JoinRoomRequest capturedRequest = captor.getValue();

        assertEquals("ABCD1234", capturedRequest.roomCode());
        assertEquals("John Doe", capturedRequest.playerName());
    }

    @Test
    @DisplayName("Should return 404 when room does not exist")
    void testJoinNoExistingRoom() throws Exception {
        when(service.joinRoom(any(JoinRoomRequest.class))).thenThrow(new RoomNotFoundException("Room not found"));

        mockMvc.perform(post("/rooms/ABCD1234/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "playerName": "John Doe",
                            "roomCode": "ABCD1234"
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 409 when room is full")
    void testJoinFullRoom() throws Exception {
        when(service.joinRoom(any(JoinRoomRequest.class))).thenThrow(new RoomFullException("Room ABCD1234 is full"));

        mockMvc.perform(post("/rooms/ABCD1234/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "playerName": "John Doe",
                            "roomCode": "ABCD1234"
                        }
                        """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Room ABCD1234 is full"));
    }
}
