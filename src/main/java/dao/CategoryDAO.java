package dao;

import config.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Category;

public class CategoryDAO {
    private final Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        String sql = "select * from category";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = new Category(
                        rs.getLong("category_id"),
                        rs.getString("name"),
                        rs.getInt("sort_order"),
                        rs.getString("insert_date"),
                        rs.getString("modify_date")
                );
                categories.add(category);
            }
            categories = categories.stream().sorted(Comparator.nullsLast(Comparator.comparingInt(Category::getSort_order))).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

}
