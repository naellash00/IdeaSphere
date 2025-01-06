package com.example.ideasphere.Controller;

import com.example.ideasphere.DTOsIN.CompanyOrganizerDTOIn;
import com.example.ideasphere.Service.CompanyOrganizerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = CompanyOrganizerController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(SpringExtension.class)
class CompanyOrganizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CompanyOrganizerService companyOrganizerService;

    @InjectMocks
    private CompanyOrganizerController companyOrganizerController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProfile() throws Exception {
        // Arrange
        Integer mockUserId = 1;
        when(companyOrganizerService.getMyProfile(mockUserId)).thenReturn(null); // Mocked response

        // Act & Assert
        mockMvc.perform(get("/api/v1/company-organizer/get-profile")
                        .requestAttr("user", mockUserId)) // Simulate @AuthenticationPrincipal
                .andExpect(status().isOk());
        verify(companyOrganizerService, times(1)).getMyProfile(mockUserId);
    }

    @Test
    void testRegister() throws Exception {
        // Arrange
        CompanyOrganizerDTOIn companyOrganizerDTOIn = new CompanyOrganizerDTOIn(
                1, "testUser", "Password@123", "Test Name", "test@example.com",
                "Test Company", "1234567890", "contact@example.com", "0512345678"
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/company-organizer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyOrganizerDTOIn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Company Organizer is Registered"));
        verify(companyOrganizerService, times(1)).register(any(CompanyOrganizerDTOIn.class));
    }


}
