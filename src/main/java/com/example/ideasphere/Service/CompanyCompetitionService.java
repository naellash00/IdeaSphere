package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.*;
import com.example.ideasphere.DTOsOut.CompanyCompetitionDTOOut;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ideasphere.DTOsOut.FeedbackOutDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.ideasphere.DTOsIN.*;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CompanyCompetitionService {

    private final CompanyCompetitionRepository companyCompetitionRepository;
    private final CompetitionRepository competitionRepository;
    private final AuthRepository authRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipantRepository participantRepository;
    private final CompetitionPaymentRepository competitionPaymentRepository;
    private final SubmissionRepository submissionRepository;
    private final WinnerPaymentService winnerPaymentService;
    private final WinnerPaymentRepository winnerPaymentRepository;


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

        if (competition.getCountExtend() >=3) throw new ApiException("Error: you have the maximum extending of the competition ");

        if (competition.getVotingMethod().equalsIgnoreCase("By Organizer")){
            companyCompetitionExtendDTOIn.setVoteEndDate(null);
        }

        // check dates
        checkEndDateAndVoteEndDate(companyCompetition.getCompetition().getEndDate(), companyCompetitionExtendDTOIn.getEndDate(), companyCompetitionExtendDTOIn.getVoteEndDate());


        // check the competition have max Participant and make sure organizer increase Participant if he wants extend
        if (competition.getSubmissions().size() >= competition.getMaxParticipants() && companyCompetitionExtendDTOIn.getIncreaseParticipants() == 0) throw new ApiException("Error: competition have max Participant you must increase Participant if you want extend ");


        competition.setEndDate(companyCompetitionExtendDTOIn.getEndDate());
        competition.setVoteEndDate(companyCompetitionExtendDTOIn.getVoteEndDate());
        competition.setMaxParticipants(competition.getMaxParticipants() + companyCompetitionExtendDTOIn.getIncreaseParticipants());
        competition.setStatus("Ongoing");
        competition.setCountExtend(competition.getCountExtend()+1);

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


    public void addPayment(Integer user_id, CompanyCompetitionPaymentDTOIn competitionPaymentDTOIn){
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser == null) throw new ApiException("Error :user not found");

        CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(competitionPaymentDTOIn.getCompanyCompetitionId());

        if (companyCompetition == null) throw new ApiException("Error: companyCompetition not found");

        if ( !companyCompetition.getCompanyOrganizer().getId().equals(myUser.getId())) throw new ApiException("Error: this competition not belong to you, you can't make payment.");

        if (!companyCompetition.getCompetition().getStatus().equalsIgnoreCase("Waiting payment")) throw new ApiException("Error: the Competition status is ("+companyCompetition.getCompetition().getStatus()+"), you can't make payment");

        CompetitionPayment duplicatedPayment = competitionPaymentRepository.findCompetitionPaymentByCompetitionId(companyCompetition.getCompetition().getId());

        if (duplicatedPayment != null ) throw new ApiException("Error: payment already created");

        CompetitionPayment competitionPayment = new CompetitionPayment();

        competitionPayment.setPaymentMethod(competitionPaymentDTOIn.getPaymentMethod());
        competitionPayment.setPaymentStatus("Completed");
        competitionPayment.setAmount(companyCompetition.getMonetaryReward());
        competitionPayment.setCompetition(companyCompetition.getCompetition());

        competitionPaymentRepository.save(competitionPayment);

        Competition competition = companyCompetition.getCompetition();
        competition.setStatus("Ongoing");
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

        String participantWinnerName = competition.getParticipantWinner() != null? competition.getParticipantWinner().getUser().getName(): null;

        return new CompanyCompetitionDTOOut(
                competition.getId(),
                competition.getTitle(),
                competition.getDescription(),
                competition.getVotingMethod(),
                competition.getCompetitionImage(),
                competition.getVoteEndDate(),
                competition.getEndDate(),
                competition.getMaxParticipants(),
                competition.getCountExtend(),
                competition.getStatus(),
                competition.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toSet()),
                participantWinnerName,
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
    public void selectWinner(Integer competition_id, Integer submission_id) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(competition.getId());
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        WinnerPayment winnerPayment = new WinnerPayment();
        if (competition == null || companyCompetition == null) {
            throw new ApiException("competition not found");
        }
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        // check submission is for this competition
        if (!submission.getCompetition().getId().equals(competition.getId())) {
            throw new ApiException("Incorrect submission for competition");
        }

        competition.setParticipantWinner(submission.getParticipant());

//        // winner payment
//        winnerPayment.setParticipantWinner(submission.getParticipant());
//        winnerPayment.setCompetition(competition);
//        winnerPayment.setAmount(companyCompetition.getMonetaryReward());
//        winnerPayment.setTransferDate(LocalDateTime.now());
//        winnerPayment.setTransferStatus("Completed");
//        winnerPayment.setTransferMethod("Bank Transfer");
//        winnerPayment.setCreatedAt(LocalDateTime.now());
//        winnerPayment.setUpdatedAt(LocalDateTime.now());

        winnerPaymentService.completeWinnerPaymentDetails(competition.getId(), submission.getId());

        competitionRepository.save(competition);
        companyCompetitionRepository.save(companyCompetition);
        winnerPaymentRepository.save(winnerPayment);
    }

    //Naelah
    public void assignVotingCompetitionWinner(Integer competition_id) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        // set of this competition submissions
        Set<Submission> submissions = competition.getSubmissions();
        if (competition == null) {
            throw new ApiException("competition not found");
        }
        if(submissions.isEmpty()){
            throw new ApiException("no submissions found");
        }

        Participant winnerParticipant = null;
        int maxNumberOfVotes = 0;

        for (Submission submission : submissions) {
            // the number of votes in this submission
            int numberOfVotes = submission.getVotes().size();
            if (numberOfVotes > maxNumberOfVotes) {
                maxNumberOfVotes = numberOfVotes;
                // set the winner
                winnerParticipant = submission.getParticipant();
                winnerPaymentService.completeWinnerPaymentDetails(competition.getId(), submission.getId());
            }
        }

        if(winnerParticipant == null){
            throw new ApiException("winner of this competition not found");
        }
        // set the winner in the competition
        competition.setParticipantWinner(winnerParticipant);
        competitionRepository.save(competition);
    }

    //Naelah
    public void acceptFeedbackRequest(Integer company_organizer_id, Integer submission_id, String feedback){
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if(submission == null){
            throw new ApiException("submission not found");
        }
        if(!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")){
            throw new ApiException("submission dose not have feedback request");
        }
        if(!submission.getCompetition().getCompanyCompetition().getCompanyOrganizer().getId().equals(company_organizer_id)){
            throw new ApiException("cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Accepted");
        submission.setOrganizerFeedback(feedback);
        //FeedbackOutDTO feedbackOutDTO = new FeedbackOutDTO("Accepted", feedback);
        submissionRepository.save(submission);
    }

    //Naelah
    public void rejectFeedbackRequest(Integer company_organizer_id, Integer submission_id){
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if(submission == null){
            throw new ApiException("submission not found");
        }
        if(!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")){
            throw new ApiException("submission dose not have feedback request");
        }
        if(!submission.getCompetition().getCompanyCompetition().getCompanyOrganizer().getId().equals(company_organizer_id)){
            throw new ApiException("cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Rejected");
        submission.setOrganizerFeedback("Request Is Rejected");
        submissionRepository.save(submission);
    }

    //Naelah
    public List<String> getMyCompetitionReviews(Integer competition_id){
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if(competition == null){
            throw new ApiException("competition not found");
        }
        List<String> reviews = new ArrayList<>();
        for(String review : competition.getReviews()){
            reviews.add(review);
        }
        return reviews;
    }
}
