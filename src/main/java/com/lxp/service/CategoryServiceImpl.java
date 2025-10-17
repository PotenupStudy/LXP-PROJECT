package com.lxp.service;

import com.lxp.dao.CategoryDAO;
import com.lxp.model.Category;
import com.lxp.util.InputUtil;
import com.lxp.util.Validator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;
    private final Scanner sc = new Scanner(System.in);

    //테스트용 생성자
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }


    public CategoryServiceImpl(Connection conn) {
        this(new CategoryDAO(conn)); // this()를 사용하여 DAO를 주입하는 생성자를 호출
    }

    @Override
    public List<Category> getAllCategories() throws SQLException, RuntimeException {
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryDAO.getAllCategory();
            if (categories.isEmpty()) {
                throw new RuntimeException("존재하는 카테고리가 없습니다.");
            }
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("[%d] %s\n", i + 1, categories.get(i).getCategory_name());
            }
        } catch (SQLException e) {
            throw new SQLException("카테고리를 가져오는 도중 오류가 발생했습니다.");
        }
        return categories;
    }

    @Override
    public Category selectCategory() throws SQLException, RuntimeException {
        List<Category> categories = getAllCategories();
        int select;
        int categorySize = categories.size();
        boolean check;

        do {
            select = InputUtil.readValidInt();
            check = Validator.selectValidator(select, categorySize);
        } while (check);

        return categories.get(select - 1);
    }

    @Override
    public void saveCategory(String categoryName) throws IllegalArgumentException, SQLException {
        Category savedCategory;
        do {
            savedCategory = Category.forNewCreate(categoryName);
            Long affectRow = categoryDAO.saveCategory(savedCategory);
            if (affectRow > 0) {
                System.out.printf("[%s]카테고리가 생성되었습니다.", savedCategory.getCategory_name());
            }
        } while (savedCategory == null);
    }

    @Override
    public void deleteCategory() throws SQLException, RuntimeException {
        Category deletedCategory = selectCategory();
        categoryDAO.deleteCategoryById(deletedCategory);
    }

    @Override
    public void updateCategoryName() throws SQLException, IllegalArgumentException {
        Category tmp = selectCategory();

        boolean isUpdateSuccessful = false;
        do {
            String updateCategoryName = InputUtil.readString("변경할 이름을 입력해주세요");

            Category updatedCategory = Category.forUpdate(tmp.getCategory_id(), updateCategoryName);
            try {
                categoryDAO.updateCategoryName(updatedCategory);
                System.out.printf("[%s]의 이름이 [%s]로 변경되었습니다.\n", tmp.getCategory_name(),
                        updatedCategory.getCategory_name());

                isUpdateSuccessful = true;
            } catch (IllegalArgumentException | SQLException e) {
                System.out.println(e.getMessage());
            }
        } while (!isUpdateSuccessful);

    }
}