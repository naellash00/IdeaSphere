package com.example.ideasphere.Controller;

import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.Service.ParticipantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ParticipantController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private ParticipantController participantController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetAllParticipants() throws Exception {
        // Example: Mocking a Service Call
        Mockito.when(participantService.getAllParticipants()).thenReturn(List.of());

        // Perform a GET request to the endpoint and expect a 200 OK response
        mockMvc.perform(get("/participants"))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterParticipant() throws Exception {
        // Arrange
        ParticipantInDTO participantInDTO = new ParticipantInDTO(
                "testUser",
                "Test@123",
                "Test Name",
                "test@example.com",
                "SA1234567890123456789012",
                Collections.emptySet()
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/participant/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantInDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Participant Registered Successfully"));
        verify(participantService, times(1)).register(any(ParticipantInDTO.class));
    }

    @Test
    void testUpdateParticipant() throws Exception {
        // Arrange
        Integer participantId = 1;
        ParticipantInDTO participantInDTO = new ParticipantInDTO(
                "updatedUser",
                "Updated@123",
                "Updated Name",
                "updated@example.com",
                "SA1234567890123456789012",
                Collections.emptySet()
        );

        // Act & Assert
        mockMvc.perform(put("/api/v1/participant/update/{id}", participantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantInDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Participant Updated Successfully"));
        verify(participantService, times(1)).updateParticipant(eq(participantId), any(ParticipantInDTO.class));
    }
}