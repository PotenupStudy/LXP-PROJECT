package com.lxp.service;

import com.lxp.dao.CourseDao;
import com.lxp.dao.SectionDao;
import com.lxp.model.Section;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SectionService {
    private final SectionDao sectionDao;
    private final CourseDao courseDao;

    public SectionService(Connection connection) {
        this.sectionDao = new SectionDao(connection);
        this.courseDao = new CourseDao(connection);
    }

    public long saveSection(Section section) {
        try {
            // 강좌 존재하는지 확인
            if (!courseDao.existById(section.getCourseId())) {
                System.out.println("존재하지 않는 강좌입니다.");
                return 0;
            }

            // 해당 강좌에 순서가 중복되는지 확인
            if (sectionDao.existsSectionByCourseIdAndOrderNum(section.getCourseId(), section.getOrderNum())) {
                System.out.println("이미 존재하는 섹션 번호입니다.");
                return 0;
            }


            long result = sectionDao.save(section);

            if (result < 0) {
                System.out.println("과정 등록 실패");
                return 0;
            } else {
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("데이터베이스 오류 발생");
        }
        return 0;
    }

    public boolean sectionOrderExists(long courseId, int orderNum) throws SQLException {
        return sectionDao.existsSectionByCourseIdAndOrderNum(courseId, orderNum);
    }

    public List<Section> findSectionsByCourseId(long courseId) {
        try {
            // DAO를 호출하여 데이터를 가져옵니다.
            List<Section> sections = sectionDao.findByCourseId(courseId);

            if (sections.isEmpty()) {
                return Collections.emptyList();
            }

            return sections;
        } catch (SQLException e) {
            System.err.println("섹션 목록을 조회하는 중 오류가 발생했습니다.");
            e.printStackTrace();
            // 비어있는 리스트를 반환하여 안전하게 처리합니다.
            return Collections.emptyList();
        }
    }

    public boolean updateSection(Section section) {
        try {
            // 수정하려는 섹션이 실제로 존재하는지 먼저 확인
            if (!sectionDao.existsSectionByCourseIdAndOrderNum(section.getCourseId(), section.getOrderNum())) {
                System.out.println("Service Log: 수정할 섹션이 존재하지 않습니다.");
                return false;
            }
            return sectionDao.update(section);
        } catch (SQLException e) {
            System.err.println("섹션 수정 중 오류 발생");
            e.printStackTrace();
            return false;
        }
    }

    // --- ✨ 2. 섹션 삭제 서비스 메서드 추가 ✨ ---
    public boolean deleteSection(long courseId, int orderNum) {
        try {
            // 삭제하려는 섹션이 실제로 존재하는지 먼저 확인
            if (!sectionDao.existsSectionByCourseIdAndOrderNum(courseId, orderNum)) {
                System.out.println("Service Log: 삭제할 섹션이 존재하지 않습니다.");
                return false;
            }
            return sectionDao.deleteSection(courseId, orderNum);
        } catch (SQLException e) {
            System.err.println("섹션 삭제 중 오류 발생");
            e.printStackTrace();
            return false;
        }
    }
}
