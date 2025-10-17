package com.lxp.dao;


import com.lxp.model.Lecture;
import com.lxp.model.LectureResource;
import com.lxp.model.ResourceType;
import com.lxp.util.QueryUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LectureDAO {
    private final Connection connection;

    public LectureDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Lecture> findAll(long userInputSectionId) throws SQLException {
        List<Lecture> list = new ArrayList<>();
        String sql = QueryUtil.getQuery("lecture.findAll");

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, userInputSectionId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                long lectureId = rs.getLong("lecture_id");
                long sectionId = rs.getLong("section_id");
                String title = rs.getString("title");
                int lectureOrder = rs.getInt("lecture_order");
                String description = rs.getString("description");

                Lecture lecture = new Lecture(lectureId, sectionId, title, description, lectureOrder);
                list.add(lecture);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("DB 오류");
            throw new RuntimeException(e);
        }
    }

    public long create(Lecture lecture) throws SQLException {
        String sql = QueryUtil.getQuery("lecture.create");

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, lecture.getSection_id());
            pstmt.setString(2, lecture.getTitle());
            pstmt.setString(3, lecture.getDescription());
            pstmt.setInt(4, lecture.getLecture_order());

            int result = pstmt.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public long update(Lecture lecture) throws SQLException {
        String sql = QueryUtil.getQuery("lecture.update");
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, lecture.getTitle());
            pstmt.setString(2, lecture.getDescription());
            pstmt.setInt(3, lecture.getLecture_order());
            pstmt.setLong(4, lecture.getLecture_id());
            pstmt.setLong(5, lecture.getSection_id());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long delete(Lecture lecture) throws SQLException {
        String sql = QueryUtil.getQuery("lecture.delete");
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, lecture.getLecture_id());

            int result = pstmt.executeUpdate();
            if (result < 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("DB 삭제 중 오류 발생");
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<LectureResource> findAllLr(long userInputLectureId) throws SQLException {
        String sql = QueryUtil.getQuery("lr.findAll");
        List<LectureResource> list = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, userInputLectureId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    long lectureId = rs.getLong("lecture_id");
                    long resourceId = rs.getLong("resource_id");
                    String resourceName = rs.getString("resource_name");
                    String typeStr = rs.getString("resource_type");
                    ResourceType type = ResourceType.fromDb(typeStr);
                    String resourceUrl = rs.getString("resource_url");
                    int orderIndex = rs.getInt("order_index");
                    int duration = rs.getInt("duration");

                    LectureResource resource = new LectureResource(resourceId, lectureId, resourceName, type, resourceUrl, orderIndex, duration);

                    list.add(resource);
                }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return list;
    }

    public long createLr(LectureResource resource) throws SQLException {
        String sql = QueryUtil.getQuery("lr.create");

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, resource.getLecture_id());
            pstmt.setString(2, resource.getResource_name());
            pstmt.setString(3, resource.getResource_type().getDbValue());
            pstmt.setString(4, resource.getResource_url());
            pstmt.setInt(5,resource.getOrder_index());
            pstmt.setInt(6, resource.getDuration());

            int result = pstmt.executeUpdate();

            if(result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            } return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public long updateLr(LectureResource resource) throws SQLException {
        String sql = QueryUtil.getQuery("lr.update");
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, resource.getResource_name());
            pstmt.setString(2, resource.getResource_type().getDbValue());
            pstmt.setString(3, resource.getResource_url());
            pstmt.setInt(4,resource.getOrder_index());
            pstmt.setInt(5, resource.getDuration());
            pstmt.setLong(6, resource.getResource_id());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long deleteLr(long resourceId) throws SQLException {
        String sql = QueryUtil.getQuery("lr.delete");
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, resourceId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
