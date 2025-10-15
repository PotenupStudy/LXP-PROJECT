package com.lxp.dao;

import com.lxp.model.Section;
import com.lxp.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SectionDao {
    private final Connection connection;

    public SectionDao(Connection connection) {
        this.connection = connection;
    }

    public long save(Section section) throws SQLException {
        String sql = QueryUtil.getQuery("section.save");

        try (PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setLong(1, section.getCourseId());
            prepareStatement.setString(2, section.getTitle());
            prepareStatement.setInt(3, section.getOrderNum());

            int result = prepareStatement.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = prepareStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new SQLException("강좌 생성에 실패하엿습니다.");
    }


    public boolean existsSectionByCourseIdAndOrderNum(long courseId, int orderNum) throws SQLException {
        String sql = QueryUtil.getQuery("section.existByCourseIdAndOrderNum");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, courseId);
            pstmt.setInt(2, orderNum);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        // 쿼리 결과가 비정상적인 경우
        return false;
    }

    public List<Section> findByCourseId(long courseId) throws SQLException {
        List<Section> sections = new ArrayList<>();
        String sql = QueryUtil.getQuery("section.findByCourseId");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, courseId);

            ResultSet rs = pstmt.executeQuery();
            // 결과가 여러 개일 수 있으므로 while 문으로 순회합니다.
            while (rs.next()) {
                // ResultSet에서 데이터를 가져와 Section 객체를 생성합니다.
                String title = rs.getString("title");
                int orderNum = rs.getInt("order_num");

                // courseId는 입력받은 값을 그대로 사용합니다.
                Section section = new Section(courseId, title, orderNum);
                sections.add(section);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("섹션 조회시 오류가 발생했습니다.");
        }

        return sections; // 조회된 섹션 리스트를 반환합니다.
    }

    public boolean update(Section section) throws SQLException {
        String sql = QueryUtil.getQuery("section.update");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, section.getTitle());
            pstmt.setLong(2, section.getCourseId());
            pstmt.setInt(3, section.getOrderNum());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // 1개 이상의 행이 변경되었으면 true 반환
        }
    }

    public boolean deleteSection(long courseId, int orderNum) throws SQLException {
        String sql = QueryUtil.getQuery("section.delete");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, courseId);
            pstmt.setInt(2, orderNum);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // 1개 이상의 행이 삭제되었으면 true 반환
        }
    }
}
