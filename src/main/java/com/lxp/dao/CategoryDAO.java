package com.lxp.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import com.lxp.model.Category;
import com.lxp.util.QueryUtil;


public class CategoryDAO {
    private final Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Category> getAllCategory() throws SQLException {
        List<Category> categories = new ArrayList<>();;
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
        }catch (SQLException e){
            throw new RuntimeException("Error in getting all categories");
        }
        return categories;
    }

    public Long saveCategory(Category category) throws SQLException {
        String sql = QueryUtil.getQuery("category.save");
        try (PreparedStatement pstmt = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getCategory_name());
            int result = pstmt.executeUpdate();
            return resultSave(result, pstmt);
        }catch (SQLException e){
            throw new SQLException("Error in saving category");
        }
    }

    private Long resultSave(int result, PreparedStatement pstmt) throws SQLException {
        if (result > 0) {
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }catch (SQLException e){
                throw new SQLException("카테고리를 저장하는 도중 DB오류가 발생했습니다.");
            }
        }
        throw new SQLException("카테고리 생성에 실패 했습니다.");
    }

    public void deleteCategoryById(Category category) throws SQLException,RuntimeException {
        String sql = QueryUtil.getQuery("category.delete");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, category.getCategory_id());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                throw new SQLException("삭제하려는 데이터가 존재하지 않습니다.");
            }
        }catch (SQLException e){
            throw new RuntimeException("Error in deleting category");
        }
    }

    public void updateCategoryName(Category category) throws SQLException {
        String sql = QueryUtil.getQuery("category.updateName");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getCategory_name());
            pstmt.setLong(2, category.getCategory_id());
            int result =pstmt.executeUpdate();
            if (result == 0) {
                throw new SQLException("수정하려는 데이터가 존재하지 않습니다.");
            }
        }catch (SQLIntegrityConstraintViolationException e){
            throw new IllegalArgumentException("카테고리 이름은 중복 될 수 없습니다.");
        }
    }
}
