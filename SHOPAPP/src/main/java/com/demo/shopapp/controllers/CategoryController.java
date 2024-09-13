package com.demo.shopapp.controllers;

import com.demo.shopapp.dtos.CategoryDTO;
import com.demo.shopapp.model.Category;
import com.demo.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> createCatelories(@Valid @RequestBody Category Category) {
        categoryService.createdCategory(Category);
        return ResponseEntity.ok("Insert Categories" + Category);
    }

    @GetMapping("")
    public ResponseEntity<String> getAllCatelories(@RequestParam("page") int page,
                                                   @RequestParam("limit") int limit) {
        return ResponseEntity.ok("Insert Categories with page: " + page + ", limit: " + limit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCatelories(@PathVariable long id) {
        return ResponseEntity.ok("Update categories with id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCatelories(@PathVariable long id) {
        return ResponseEntity.ok("Delete Categories with id:" + id);
    }
}
