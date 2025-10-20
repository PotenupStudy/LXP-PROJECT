package com.lxp.controller;

import com.lxp.model.Category;
import com.lxp.service.CategoryService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//카테고리 전체 조회 -> List<Category> getAllCategories()
//카테고리 추가 -> void saveCategory()
//카테고리삭제 -> void deleteCategory()
//수정 -> void updateCategoryName()
//5. 불러온 카테고리중 인덱스 번호로 선택 -> Category selectCategory()

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
            System.out.println(e.getMessage());
        }
        return categories;
    }
    public Category getCategoryByIndex() throws RuntimeException, SQLException {
        Category category = null;
        try{
            category = categoryService.selectCategory();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return category;
    }
    public void createCategory(String categoryName){
        try{
            categoryService.saveCategory(categoryName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void deleteCategory() throws RuntimeException, SQLException {
        try{
            categoryService.deleteCategory();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void updateCategory() throws RuntimeException, SQLException {
        try{
            categoryService.updateCategoryName();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
