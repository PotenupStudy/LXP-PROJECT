package com.lxp.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import com.lxp.utill.Logging;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.lxp.model.Category;
import com.lxp.utill.QueryUtil;


public class CategoryDAO {
    private final Connection conn;
    private final Logging logger;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
        this.logger = new Logging(CategoryDAO.class);
    }

    public List<Category> getAllCategory() throws SQLException {
        List<Category> categories = new ArrayList<>();;
        String sql = QueryUtil.getQuery("category.getAllCategories");
        logger.info("[getAllCategory] DB 조작 시작");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            logger.debug("SQL : {}",sql);
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
            logger.error("[getAllCategory] DB통신 오류 발생 :",e);
            throw e;
        }
        logger.info("총 {}개의 카테고리 조회",categories.size());
        
        //logger.info("{}",categories);
        return categories;
    }

    public Long saveCategory(Category category) throws SQLException {
        logger.info("[Saved Category] DB 조작 시작");
        String sql = QueryUtil.getQuery("category.save");
        try (PreparedStatement pstmt = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getCategory_name());
            int result = pstmt.executeUpdate();
            logger.debug("[saveCategory] result  : {}", result);
            return resultSave(result, pstmt);
        }
    }

    private Long resultSave(int result, PreparedStatement pstmt) throws SQLException {
        if (result > 0) {
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }catch (SQLException e){
                logger.error("[resultSave] DB통신 오류 발생 : ",e);
                throw new SQLException("카테고리를 저장하는 도중 DB오류가 발생했습니다.");
            }
        }
        throw new SQLException("카테고리 생성에 실패 했습니다.");
    }

    public void deleteCategoryById(Category category) throws SQLException {
        logger.info("[Delete Category By Id] DB 조작 시작");
        String sql = QueryUtil.getQuery("category.delete");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, category.getCategory_id());
            int result = pstmt.executeUpdate();
            logger.debug("[deleteCategory] result  : {}", result);
            if (result == 0) {
                throw new SQLException("삭제하려는 데이터가 존재하지 않습니다.");
            }
        }catch (SQLException e){
            logger.error("[deleteCategoryById] DB통신 오류 발생 : ",e);
            throw e;
        }
    }

    public Category updateCategoryName(Category category) throws SQLException {
        logger.info("[Update CategoryName] DB 조작 시작");
        String sql = QueryUtil.getQuery("category.updateName");
        try (PreparedStatement pstmt = conn.prepareStatement(sql,RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getCategory_name());
            pstmt.setLong(2, category.getCategory_id());
            int result =pstmt.executeUpdate();
            logger.debug("[updateCategoryName] result  : {}", result);
            if (result == 0) {
                throw new SQLException("수정하려는 데이터가 존재하지 않습니다.");
            }
        }catch (SQLException e){
            logger.error("[updateCategoryName] DB통신 오류러 발생 : {}",e);
            throw e;
        }
        return category;
    }
}
