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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ParticipantRepository participantRepository;

    // made by admin
    public void uploadCategory(Integer admin_id, Category category) {
        categoryRepository.save(category);
    }

    // Naelah
    public void addCategory(Integer participant_id, Integer category_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        // check if the category exist in the system
        Category category = categoryRepository.findCategoryById(category_id);
        if (participant == null) {
            throw new ApiException("Participant not fount");
        }
        if (category == null) {
            throw new ApiException("Category dose not Exist");
        }
        participant.getCategories().add(category);
        participantRepository.save(participant);
    }
}
