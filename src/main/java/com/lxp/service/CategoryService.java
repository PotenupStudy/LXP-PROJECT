package com.lxp.service;

import com.lxp.model.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories() throws SQLException, RuntimeException;

    Category selectCategory()throws SQLException, RuntimeException;

    void saveCategory(String categoryName) throws IllegalArgumentException, SQLException;

    void deleteCategory() throws SQLException,RuntimeException;

    void updateCategoryName() throws SQLException,RuntimeException;
}
