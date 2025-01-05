package com.example.ideasphere.Controller;

import com.example.ideasphere.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.Category;
import com.example.ideasphere.Model.MyUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // created by admin
    @PostMapping("/upload-category")
    public ResponseEntity uploadCategoryInSystem(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid Category category) {
        categoryService.uploadCategoryInSystem(myUser.getId(), category);
        return ResponseEntity.status(200).body(new ApiResponse("category added in the website successfully"));
    }

    // for participant
    @PutMapping("/add/category/to/participant/{category_id}")
    public ResponseEntity addCategoryToMyProfile(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer category_id) {
        categoryService.addCategoryToMyProfile(myUser.getId(), category_id);
        return ResponseEntity.status(200).body(new ApiResponse("Category Added To Your Profile Successfully"));
    }
}
