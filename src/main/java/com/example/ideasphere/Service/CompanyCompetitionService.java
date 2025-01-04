package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.*;
import com.example.ideasphere.DTOsOut.CompanyCompetitionDTOOut;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyCompetitionService {

    private final CompanyCompetitionRepository companyCompetitionRepository;
    private final CompetitionRepository competitionRepository;
    private final AuthRepository authRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipantRepository participantRepository;


    public List<CompanyCompetitionDTOOut> getAllCompanyCompetition(){
        List<CompanyCompetition> companyCompetitions = companyCompetitionRepository.findAll();

        return companyCompetitions.stream()
                .map(this::ConvertDTO)
                .collect(Collectors.toList());
    }

    public List<CompanyCompetitionDTOOut> getMyCompetition(Integer user_id){
        List<CompanyCompetition> companyCompetitions = companyCompetitionRepository.findCompanyCompetitionByCompanyOrganizerId(user_id);

        return companyCompetitions.stream()
                .map(this::ConvertDTO)
                .collect(Collectors.toList());
    }



    public void createCompetitionFinancialInterview(Integer userId, CompanyCompetitionFinancialInterviewDTOIn competitionDTO) {
        MyUser myUser = authRepository.findMyUserById(userId);

        if (myUser == null) throw new ApiException("Error: user not found");

        if (!myUser.getCompanyOrganizer().getStatus().equalsIgnoreCase("Active")) throw new ApiException("Error : CompanyOrganizer not active");

        // validation check Category Exist
        checkCategoryExist(competitionDTO.getCategories());
        // validation check dates
        checkEndDateAndVoteEndDate(competitionDTO.getEndDate(), null);
        // Map DTO to entity
        Competition competition = new Competition();
        competition.setTitle(competitionDTO.getTitle());
        competition.setDescription(competitionDTO.getDescription());
        competition.setCompetitionImage(competitionDTO.getCompetitionImage());
        competition.setCategories(competitionDTO.getCategories());
        competition.setEndDate(competitionDTO.getEndDate());
        competition.setMaxParticipants(competitionDTO.getMaxParticipants());
        competition.setVotingMethod("By Organizer");
        competition.setStatus("Waiting payment");
        competition = competitionRepository.save(competition);
        CompanyCompetition companyCompetition = new CompanyCompetition();

        companyCompetition.setMonetaryReward(competitionDTO.getMonetaryReward());
        companyCompetition.setCompanyOrganizer(myUser.getCompanyOrganizer());
        companyCompetition.setRewardType("Financial&Interview");
        companyCompetition.setCompetition(competition);
        companyCompetitionRepository.save(companyCompetition);
    }

    public void createCompetitionInterview(Integer userId, CompanyCompetitionInterviewDTOIn competitionDTO) {
        MyUser myUser = authRepository.findMyUserById(userId);

        if (myUser == null) throw new ApiException("Error: user not found");

        if (!myUser.getCompanyOrganizer().getStatus().equalsIgnoreCase("Active")) throw new ApiException("Error : CompanyOrganizer not active");

        // validation check Category Exist
        checkCategoryExist(competitionDTO.getCategories());
        // validation check dates
        checkEndDateAndVoteEndDate(competitionDTO.getEndDate(), null);
        // Map DTO to entity
        Competition competition = new Competition();
        competition.setTitle(competitionDTO.getTitle());
        competition.setDescription(competitionDTO.getDescription());
        competition.setCompetitionImage(competitionDTO.getCompetitionImage());
        competition.setCategories(competitionDTO.getCategories());
        competition.setEndDate(competitionDTO.getEndDate());
        competition.setMaxParticipants(competitionDTO.getMaxParticipants());
        competition.setVotingMethod("By Organizer");
        competition.setStatus("Ongoing");
        competition = competitionRepository.save(competition);
        CompanyCompetition companyCompetition = new CompanyCompetition();

        companyCompetition.setMonetaryReward(0.0);
        companyCompetition.setCompanyOrganizer(myUser.getCompanyOrganizer());
        companyCompetition.setRewardType("Interview");
        companyCompetition.setCompetition(competition);

        companyCompetitionRepository.save(companyCompetition);
    }

    public void createCompetitionFinancialByVoteDTOIn(Integer userId, CompanyCompetitionFinancialByVoteDTOIn competitionDTO) {
        MyUser myUser = authRepository.findMyUserById(userId);

        if (myUser == null) throw new ApiException("Error: user not found");

        if (!myUser.getCompanyOrganizer().getStatus().equalsIgnoreCase("Active")) throw new ApiException("Error : CompanyOrganizer not active");
        // validation check Category Exist
        checkCategoryExist(competitionDTO.getCategories());

        // validation check dates
        checkEndDateAndVoteEndDate(competitionDTO.getEndDate(), competitionDTO.getVoteEndDate());

        // Map DTO to entity
        Competition competition = new Competition();
        competition.setTitle(competitionDTO.getTitle());
        competition.setDescription(competitionDTO.getDescription());
        competition.setCompetitionImage(competitionDTO.getCompetitionImage());
        competition.setCategories(competitionDTO.getCategories());
        competition.setVoteEndDate(competitionDTO.getVoteEndDate());
        competition.setEndDate(competitionDTO.getEndDate());
        competition.setMaxParticipants(competitionDTO.getMaxParticipants());
        competition.setVotingMethod("By Public Vote");
        competition.setStatus("Waiting payment");
        competition = competitionRepository.save(competition);

        CompanyCompetition companyCompetition = new CompanyCompetition();

        companyCompetition.setMonetaryReward(competitionDTO.getMonetaryReward());
        companyCompetition.setCompanyOrganizer(myUser.getCompanyOrganizer());
        companyCompetition.setRewardType("Financial");
        companyCompetition.setCompetition(competition);

        companyCompetitionRepository.save(companyCompetition);
    }


    public void createCompetitionFinancialByOrganizerDTOIn(Integer userId, CompanyCompetitionFinancialByOrganizerDTOIn competitionDTO) {
        MyUser myUser = authRepository.findMyUserById(userId);

        if (myUser == null) throw new ApiException("Error: user not found");

        if (!myUser.getCompanyOrganizer().getStatus().equalsIgnoreCase("Active")) throw new ApiException("Error : CompanyOrganizer not active");

        // validation check Category Exist
        checkCategoryExist(competitionDTO.getCategories());
        // validation check dates
        checkEndDateAndVoteEndDate(competitionDTO.getEndDate(), null);

        // Map DTO to entity
        Competition competition = new Competition();
        competition.setTitle(competitionDTO.getTitle());
        competition.setDescription(competitionDTO.getDescription());
        competition.setCompetitionImage(competitionDTO.getCompetitionImage());
        competition.setCategories(competitionDTO.getCategories());
        competition.setEndDate(competitionDTO.getEndDate());
        competition.setMaxParticipants(competitionDTO.getMaxParticipants());
        competition.setVotingMethod("By Organizer");
        competition.setStatus("Waiting payment");
        competition = competitionRepository.save(competition);
        CompanyCompetition companyCompetition = new CompanyCompetition();

        companyCompetition.setMonetaryReward(competitionDTO.getMonetaryReward());
        companyCompetition.setCompanyOrganizer(myUser.getCompanyOrganizer());
        companyCompetition.setRewardType("Financial");
        companyCompetition.setCompetition(competition);

        companyCompetitionRepository.save(companyCompetition);
    }
    public void extendCompetition(Integer user_id , CompanyCompetitionExtendDTOIn companyCompetitionExtendDTOIn){
        MyUser myUser = authRepository.findMyUserById(user_id);

        if (myUser == null) throw new ApiException("Error: user not found");

        CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(companyCompetitionExtendDTOIn.getId());

        if (companyCompetition == null) throw new ApiException("Error: companyCompetition not found");

        if (!myUser.getCompanyOrganizer().getId().equals(companyCompetition.getCompanyOrganizer().getId())) throw new ApiException("Error: this Competition not belong to you");

        String status =  companyCompetition.getCompetition().getStatus();
        // check status
        if (status.equalsIgnoreCase("Completed")
        || status.equalsIgnoreCase("Waiting payment" )
        || status.equalsIgnoreCase("canceled" )) throw new ApiException("Error: the Competition status is ("+status+") you can't extend the Competition") ;

        Competition competition = companyCompetition.getCompetition();

        if (competition.getVotingMethod().equalsIgnoreCase("By Organizer")){
            companyCompetitionExtendDTOIn.setVoteEndDate(null);
        }

        // check dates
        checkEndDateAndVoteEndDate(companyCompetition.getCompetition().getEndDate(), companyCompetitionExtendDTOIn.getEndDate(), companyCompetitionExtendDTOIn.getVoteEndDate());




        competition.setEndDate(companyCompetitionExtendDTOIn.getEndDate());
        competition.setVoteEndDate(companyCompetitionExtendDTOIn.getVoteEndDate());
        competition.setMaxParticipants(competition.getMaxParticipants() + companyCompetitionExtendDTOIn.getIncreaseParticipants());
        competition.setStatus("Ongoing");

        competitionRepository.save(competition);
    }
    public void updateCompetition(Integer user_id , CompanyCompetitionUpdateDTOIn companyCompetitionUpdateDTOIn){
        MyUser myUser = authRepository.findMyUserById(user_id);

        if (myUser == null) throw new ApiException("Error: user not found");

        CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(companyCompetitionUpdateDTOIn.getId());

        if (companyCompetition == null) throw new ApiException("Error: companyCompetition not found");

        if (!myUser.getCompanyOrganizer().getId().equals(companyCompetition.getCompanyOrganizer().getId())) throw new ApiException("Error: this Competition not belong to you");

        String status =  companyCompetition.getCompetition().getStatus();
        // check status
        if (!status.equalsIgnoreCase("Ongoing")) throw new ApiException("Error: the Competition status is ("+status+") you can't extend the Competition");

        // check category
        checkCategoryExist(companyCompetitionUpdateDTOIn.getCategories());

        Competition competition = companyCompetition.getCompetition();

        competition.setTitle(companyCompetitionUpdateDTOIn.getTitle());
        competition.setDescription(companyCompetitionUpdateDTOIn.getDescription());
        competition.setCompetitionImage(companyCompetitionUpdateDTOIn.getCompetitionImage());
        competition.setCategories(companyCompetitionUpdateDTOIn.getCategories());

        competitionRepository.save(competition);
    }
    public void checkCategoryExist(Set<Category> categories){
        for(Category category : categories){
            Category findCategory = categoryRepository.findCategoryById(category.getId());
            if (findCategory == null )throw new ApiException("Error: category not found ");
        }
    }
    public void checkEndDateAndVoteEndDate(LocalDate endDate, LocalDate voteEndDate) {
        LocalDate today = LocalDate.now();

        // Check EndDate conditions
        if (endDate.isBefore(today) || endDate.isEqual(today)) {
            throw new ApiException("Error: EndDate must be in the future and not today.");
        }
        if (endDate.isAfter(today.plusDays(30))) {
            throw new ApiException("Error: EndDate cannot be more than 30 days from today.");
        }

        // If VoteEndDate is provided, check its conditions
        if (voteEndDate != null) {
            if (voteEndDate.isBefore(endDate) || voteEndDate.isEqual(endDate)) {
                throw new ApiException("Error: VoteEndDate must be after EndDate.");
            }
            if (voteEndDate.isAfter(endDate.plusDays(7))) {
                throw new ApiException("Error: VoteEndDate cannot be more than 7 days after EndDate.");
            }
        }
    }

    public CompanyCompetitionDTOOut ConvertDTO(CompanyCompetition companyCompetition) {
        Competition competition = companyCompetition.getCompetition();

        return new CompanyCompetitionDTOOut(
                competition.getId(),
                competition.getTitle(),
                competition.getDescription(),
                competition.getVotingMethod(),
                competition.getCompetitionImage(),
                competition.getVoteEndDate(),
                competition.getEndDate(),
                competition.getMaxParticipants(),
                competition.getStatus(),
                competition.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toSet()),
                companyCompetition.getRewardType(),
                companyCompetition.getMonetaryReward(),
                companyCompetition.getCompanyOrganizer().getCompanyName()
        );
    }


    public void checkEndDateAndVoteEndDate(LocalDate oldEndDate,LocalDate endDate, LocalDate voteEndDate) {


        // Check EndDate conditions
        if (endDate.isBefore(oldEndDate) || endDate.isEqual(oldEndDate)) {
            throw new ApiException("Error: EndDate must be in the future and not equal oldEndDate.");
        }
        if (endDate.isAfter(oldEndDate.plusDays(30))) {
            throw new ApiException("Error: EndDate cannot be more than 30 days from oldEndDate.");
        }

        // If VoteEndDate is provided, check its conditions
        if (voteEndDate != null) {
            if (voteEndDate.isBefore(endDate) || voteEndDate.isEqual(endDate)) {
                throw new ApiException("Error: VoteEndDate must be after EndDate.");
            }
            if (voteEndDate.isAfter(endDate.plusDays(7))) {
                throw new ApiException("Error: VoteEndDate cannot be more than 7 days after EndDate.");
            }
        }
    }

    // Naelah
    public void addAchievementToWinner(Integer competition_id, Integer participant_id){
        Participant participant = participantRepository.findParticipantById(participant_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if(participant == null){
            throw new ApiException("participant not found");
        }
        if(competition == null){
            throw new ApiException("competition not found");
        }
    }
}
