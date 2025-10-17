package com.lxp.service;

import com.lxp.model.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories() throws SQLException;

    Category selectCategory() throws SQLException;

    void saveCategory() throws SQLException;

    void deleteCategory() throws SQLException;

    void updateCategoryName() throws SQLException;
}
