package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.Category;
import com.example.ideasphere.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController { // Naelah
    private final CategoryService categoryService;
    // created by admin
    @PostMapping("/upload-category/{admin_id}")
    public ResponseEntity uploadCategory(@PathVariable Integer admin_id, @RequestBody @Valid Category category) {
        categoryService.uploadCategory(admin_id, category);
        return ResponseEntity.status(200).body(new ApiResponse("category added in the website successfully"));
    }
}
