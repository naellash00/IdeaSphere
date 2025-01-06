package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsOut.CompetitionOutDTO;
import com.example.ideasphere.Model.Category;
import com.example.ideasphere.Model.Competition;
import com.example.ideasphere.Model.Participant;
import com.example.ideasphere.Repository.CompanyOrganizerRepository;
import com.example.ideasphere.Repository.CompetitionRepository;
import com.example.ideasphere.Repository.IndividualOrganizerRepository;
import com.example.ideasphere.Repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompetitionServiceTest {

    @InjectMocks
    private CompetitionService competitionService;

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private CompanyOrganizerRepository companyOrganizerRepository;

    @Mock
    private IndividualOrganizerRepository individualOrganizerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCompetition() {
        // Arrange
        Competition competition1 = new Competition();
        Competition competition2 = new Competition();
        when(competitionRepository.findAll()).thenReturn(List.of(competition1, competition2));

        // Act
        List<Competition> competitions = competitionService.getAllCompetition();

        // Assert
        assertEquals(2, competitions.size());
        verify(competitionRepository, times(1)).findAll();
    }

    @Test
    void testRecommendCompetitions_WithCategories() {
        // Arrange
        Participant participant = new Participant();
        Category category = new Category();
        participant.setCategories(Set.of(category));

        Competition competition = new Competition();
        competition.setCategories(Set.of(category));
        competition.setTitle("Test Competition");
        competition.setDescription("Description");
        competition.setVotingMethod("By Organizer");
        competition.setEndDate(LocalDate.now());
        competition.setMaxParticipants(100);

        when(participantRepository.findParticipantById(anyInt())).thenReturn(participant);
        when(competitionRepository.findAll()).thenReturn(List.of(competition));

        // Act
        List<CompetitionOutDTO> recommended = competitionService.recommendCompetitions(1);

        // Assert
        assertEquals(1, recommended.size());
        assertEquals("Test Competition", recommended.get(0).getTitle());
    }

    @Test
    void testRecommendCompetitions_NoCategories() {
        // Arrange
        Participant participant = new Participant();
        participant.setCategories(Set.of());
        when(participantRepository.findParticipantById(anyInt())).thenReturn(participant);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> competitionService.recommendCompetitions(1));
        assertEquals("participant has no categories associated with their profile.", exception.getMessage());
    }

    @Test
    void testAddReviewOnCompetition_Success() {
        // Arrange
        Competition competition = new Competition();
        competition.setSubmissions(Set.of());
        Participant participant = new Participant();
        when(competitionRepository.findCompetitionById(anyInt())).thenReturn(competition);
        when(participantRepository.findParticipantById(anyInt())).thenReturn(participant);

        // Act & Assert
        competitionService.addReviewOnCompetition(1, 1, "Great competition!");
        verify(competitionRepository, times(1)).save(competition);
    }

    @Test
    void testAddReviewOnCompetition_InvalidCompetition() {
        // Arrange
        when(competitionRepository.findCompetitionById(anyInt())).thenReturn(null);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> competitionService.addReviewOnCompetition(1, 1, "Great competition!"));
        assertEquals("competition not found", exception.getMessage());
    }
}
