package service;

import dao.CategoryDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.Category;

public class CategoryService {
    Scanner sc = new Scanner(System.in);
    Connection conn;
    CategoryDAO categoryDAO;

    public CategoryService(Connection conn) {
        this.conn = conn;
        this.categoryDAO = new CategoryDAO(conn);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryDAO.getAllCategory();
        for (Category category : categories) {
            System.out.printf("[%d] %s\n", category.sort_order(), category.category_name());
        }
        return categories;
    }

    public Category selectCategory() {
        List<Category> categories = getAllCategories();

        int choice = Integer.parseInt(sc.nextLine());

        return categories.stream()
                .filter(category -> category.sort_order() == choice)
                .findFirst()
                .orElse(null);
    }

    public void saveCategory(String addCategoryName) throws SQLException {
        categoryDAO.saveCategory(addCategoryName);
    }
}
