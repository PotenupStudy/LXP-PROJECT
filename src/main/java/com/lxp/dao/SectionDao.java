package com.lxp.dao;

import com.lxp.model.Section;
import com.lxp.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionDao {
    private final Connection connection;

    public SectionDao(Connection connection) {
        this.connection = connection;
    }

    public void saveSection(Section section) {
        String sql = QueryUtil.getQuery("section.save");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, section.getCourseId());
            ps.setString(2, section.getTitle());
            ps.setInt(3, section.getOrderNum());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("섹션 생성에 실패했습니다");
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류: 섹션 생성 실패", e);
        }
    }

    public Boolean existsBySectionId(Long sectionId) {
        String sql = QueryUtil.getQuery("section.existBySectionId");

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, sectionId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB 오류: 섹션 존재 여부 확인 실패", e);
        }
    }

    public boolean existsByCourseIdAndOrderNum(Long courseId, Integer orderNum) {
        String sql = QueryUtil.getQuery("section.existByCourseIdAndOrderNum");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ps.setInt(2, orderNum);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류: 섹션 존재 여부 확인 실패", e);
        }
    }

    public Section findSectionById(Long sectionId) {
        String sql = QueryUtil.getQuery("section.findById");

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, sectionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long findSectionId = rs.getLong("section_id");
                Long courseId = rs.getLong("course_id");
                String title = rs.getString("title");
                Integer orderNum = rs.getInt("order_num");

                return Section.from(findSectionId, courseId, title, orderNum);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류: 강좌 ID로 섹션 조회 실패", e);
        }

        return null;
    }

    public List<Section> findSectionsByCourseId(Long courseId) {
        List<Section> sections = new ArrayList<>();
        String sql = QueryUtil.getQuery("section.findByCourseId");

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long sectionId = rs.getLong("section_id");
                    String title = rs.getString("title");
                    int orderNum = rs.getInt("order_num");
                    sections.add(Section.from(sectionId, courseId, title, orderNum));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류: 강좌 ID로 섹션 조회 실패", e);
        }

        return sections;
    }

    public void updateSection(Section section) {
        String sql = QueryUtil.getQuery("section.update");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, section.getTitle());
            ps.setInt(2, section.getOrderNum());
            ps.setLong(3, section.getSectionId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("업데이트할 섹션을 찾지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류: 섹션 업데이트 실패", e);
        }
    }

    public void deleteSection(Long sectionId) {
        String sql = QueryUtil.getQuery("section.delete");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, sectionId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("삭제할 섹션을 찾지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류: 섹션 삭제 실패", e);
        }
    }
}
