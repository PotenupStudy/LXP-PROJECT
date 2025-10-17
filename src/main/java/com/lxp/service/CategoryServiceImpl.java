package com.lxp.service;

import static com.lxp.utill.Validator.selectValidator;

import com.lxp.dao.CategoryDAO;
import com.lxp.model.Category;
import com.lxp.utill.InputUtil;
import com.lxp.utill.Logging;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoryServiceImpl implements CategoryService {
    private final Logging logger;
    private final CategoryDAO categoryDAO;
    private final Scanner sc =  new Scanner(System.in);

    //테스트용 생성자
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
        this.logger = new Logging(CategoryServiceImpl.class);
    }


    public CategoryServiceImpl(Connection conn) {
        this(new CategoryDAO(conn)); // this()를 사용하여 DAO를 주입하는 생성자를 호출
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

        int categorySize = categories.size() ;

        int select = selectValidator(categorySize);

        Category selectedCategory = categories.get(select - 1);
        logger.debug("[CategoryService] Selected Category : {}", selectedCategory);

        return selectedCategory;
    }
    @Override
    public void saveCategory() throws SQLException {
        logger.info("[CategoryService] Save Category 시작");
        Category savedCategory = null;
        do {
            String categoryName = InputUtil.readString("추가할 카테고리 이름을 입력하세요: ");
            try {
                savedCategory = Category.forNewCreate(categoryName);
            } catch (IllegalArgumentException e) {
                System.err.println("⚠️ 오류: " + e.getMessage());
            }
        } while (savedCategory == null);

        logger.debug("[CategoryService] Save Category : {}", savedCategory);
        categoryDAO.saveCategory(savedCategory);
    }

    @Override
    public void deleteCategory() throws SQLException {
        logger.info("[CategoryService] Delete Category 시작");
        System.out.println("삭제할 카테고리를 선택해 주세요.");
        Category deletedCategory = selectCategory();
        logger.debug("[CategoryService] Delete Category : {}", deletedCategory);
        categoryDAO.deleteCategoryById(deletedCategory);
    }

    @Override
    public void updateCategoryName() throws SQLException {
        logger.info("[CategoryService] Update Category 시작");
        System.out.println("수정할 카테고리를 선택해 주세요.");
        Category tmp = selectCategory();

        if (tmp == null) {
            System.out.println("수정할 카테고리가 없습니다.");
            return;
        }
        boolean isUpdateSuccessful = false;
        do {
            String updateCategoryName = InputUtil.readString("변경할 이름을 입력해주세요");

            Category updatedCategory = Category.forUpdate(tmp.getCategory_id(), updateCategoryName);
            logger.debug("[CategoryService] Updated Category : {}", updatedCategory);
            try {
                categoryDAO.updateCategoryName(updatedCategory);
                System.out.printf("[%s]의 이름이 [%s]로 변경되었습니다.\n", tmp.getCategory_name(), updatedCategory.getCategory_name());

                isUpdateSuccessful = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!isUpdateSuccessful);

    }
}