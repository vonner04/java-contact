package io.github.vonner04.contact_game.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.vonner04.contact_game.dtos.CreateRoomRequest;
import io.github.vonner04.contact_game.dtos.CreateRoomResponse;
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

    }
}
