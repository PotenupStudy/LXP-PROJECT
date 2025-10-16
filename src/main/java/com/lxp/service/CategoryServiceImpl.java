package com.lxp.service;

import com.lxp.dao.CategoryDAO;
import com.lxp.model.Category;
import com.lxp.utill.Logging;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoryServiceImpl implements CategoryService {
    private final Logging logger;
    private final Connection conn;
    private final CategoryDAO categoryDAO;
    Scanner sc = new Scanner(System.in);

    public CategoryServiceImpl(Connection conn) {
        this.conn = conn;
        this.categoryDAO = new CategoryDAO(conn);
        this.logger = new Logging(CategoryServiceImpl.class);
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = categoryDAO.getAllCategory();
        logger.debug("[CategoryService] Categories : {}", categories);

        if (!categories.isEmpty()) {
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("[%d] %s\n", i + 1, categories.get(i).getCategory_name());
            }
            return categories;
        }

        System.out.println("존재하는 카테고리가 없습니다.");

        return categories;
    }

    @Override
    public Category selectCategory() throws SQLException {
        logger.info("[CategoryService] selected category 시작");
        List<Category> categories = getAllCategories();

        System.out.print("카테고리 번호를 선택하세요 : ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice < 1 || choice > categories.size()) {
            throw new IllegalArgumentException("해당 번호의 카테고리가 없습니다.");
        }

        Category selectedCategory = categories.get(choice - 1);
        logger.debug("[CategoryService] Selected Category : {}", selectedCategory);

        return selectedCategory;
    }


    @Override
    public void saveCategory(String categoryName) throws SQLException {
        logger.info("[CategoryService] Save Category 시작");
        Category savedCategory = Category.forCreation(categoryName);
        logger.debug("[CategoryService] Save Category : {}", categoryName);
        categoryDAO.saveCategory(savedCategory);
    }

    @Override
    public void deleteCategory() throws SQLException {
        logger.info("[CategoryService] Delete Category 시작");
        Category deletedCategory = selectCategory();
        logger.debug("[CategoryService] Delete Category : {}", deletedCategory);
        categoryDAO.deleteCategoryById(deletedCategory);
    }

    @Override
    public Category updateCategoryName() throws SQLException {
        logger.info("[CategoryService] Update Category 시작");
        Category updatedCategory = selectCategory();

        System.out.println("이름변경 : ");

        updatedCategory.setCategory_name(sc.nextLine());
        logger.debug("[CategoryService] Updated Category : {}", updatedCategory)
        ;
        return categoryDAO.updateCategoryName(updatedCategory);
    }
}
