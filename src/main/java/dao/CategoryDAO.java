package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Category;
import utill.QueryUtil;

public class CategoryDAO {
    private final Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        String sql = QueryUtil.getQuery("category.getAllCategories");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = Category.fromDb(
                        rs.getLong("category_id"),
                        rs.getString("name"),
                        rs.getInt("sort_order"),
                        rs.getString("insert_date"),
                        rs.getString("modify_date")
                );
                categories.add(category);
            }
            categories = categories.stream().sorted(Comparator.nullsLast(Comparator.comparingInt(Category::sort_order)))
                    .toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Long saveCategory(String addCategoryName) throws SQLException {
        String sql = QueryUtil.getQuery("category.save");
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, addCategoryName);
            int result =  pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        throw new SQLException("강좌 생성에 실패 했습니다.");
    }
}
