package com.demo.shopapp.services;

import com.demo.shopapp.dtos.CategoryDTO;
import com.demo.shopapp.model.Category;
import com.demo.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    //    public CategoryServiceImpl(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository; // DEPENDENCY INJECTOR
//    }
    @Override
    public Category createdCategory(Category Category) {

        return categoryRepository.save(Category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long cateloryId, Category Category) {
        Category existingCategory = getCategoryById(cateloryId);
        existingCategory.setName(Category.getName());
        return existingCategory;

    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
