package com.lxp.controller;

import com.lxp.model.Category;
import com.lxp.service.CategoryService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try{
            categories = categoryService.getAllCategories();

        }catch (Exception e){
            System.err.println("⚠️ 오류: " + e.getMessage());
        }
        return categories;
    }
    public Category getCategoryByIndex() throws RuntimeException, SQLException {
        Category category = null;
        try{
            category = categoryService.selectCategory();
        }catch (Exception e){
            System.err.println("⚠️ 오류: " + e.getMessage());
        }
        return category;
    }
    public void createCategory(String categoryName) throws IllegalArgumentException,SQLException {
        categoryService.saveCategory(categoryName);
    }
    public void deleteCategory() throws RuntimeException, SQLException {
        try{
            categoryService.deleteCategory();
        }catch(Exception e){
            System.err.println("⚠️ 오류: " + e.getMessage());
        }
    }
    public void updateCategory() throws RuntimeException, SQLException {
        categoryService.updateCategoryName();
    }
}
