package service;

import config.JDBCConnection;
import dao.CategoryDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.Category;

public class CategoryService {
    Scanner sc = new Scanner(System.in);

    public List<Category> getAllCategories() {
        try {
            Connection conn = JDBCConnection.getConnection();
            CategoryDAO categoryDAO = new CategoryDAO(conn);

            List<Category> categories = categoryDAO.getAllCategory();
            for (Category category : categories) {
                System.out.printf("[%d] %s\n", category.sort_order(), category.category_name());
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category selectCategory() {
        List<Category> categories = getAllCategories();

        int choice = Integer.parseInt(sc.nextLine());

        return categories.stream()
                .filter(category -> category.sort_order() == choice)
                .findFirst()
                .orElse(null);
    }

    public void saveCategory(Category category) {

    }
}
