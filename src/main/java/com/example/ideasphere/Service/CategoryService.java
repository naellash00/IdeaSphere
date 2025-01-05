package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.Category;
import com.example.ideasphere.Model.Participant;
import com.example.ideasphere.Repository.CategoryRepository;
import com.example.ideasphere.Repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService { //Naelah
    private final CategoryRepository categoryRepository;
    private final ParticipantRepository participantRepository;

    // made by admin
    public void uploadCategoryInSystem(Integer admin_id, Category category) {
        if(categoryRepository.findAll().contains(category)){
            throw new ApiException("Category exist in the system");
        }
        categoryRepository.save(category);
    }

    // Naelah
    public void addCategoryToMyProfile(Integer participant_id, Integer category_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        // check if the category exist in the system
        Category category = categoryRepository.findCategoryById(category_id);
        if (participant == null) {
            throw new ApiException("Participant not fount");
        }
        if (category == null) {
            throw new ApiException("Category dose not Exist");
        }
        if(participant.getCategories().contains(category)){
            throw new ApiException("Category is already added to your profile");
        }
        participant.getCategories().add(category);
        participantRepository.save(participant);
    }
}
