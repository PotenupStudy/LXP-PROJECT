package com.lxp.service;

import com.lxp.dao.CourseDao;
import com.lxp.dao.SectionDao;
import com.lxp.model.Course;
import com.lxp.model.Section;
import com.lxp.model.dto.ViewSectionDto;
import com.lxp.util.SignInUtil;

import java.sql.Connection;
import java.util.List;

public class SectionServiceImpl implements SectionService {
    private final SectionDao sectionDao;
    private final CourseDao courseDao;

    public SectionServiceImpl(Connection connection) {
        this.sectionDao = new SectionDao(connection);
        this.courseDao = new CourseDao(connection);
    }

    @Override
    public Boolean existsSection(Long sectionId) {
        return sectionDao.existsBySectionId(sectionId);
    }

    @Override
    public void saveSection(Long courseId, String title, Integer orderNum) {
        // 사용자가 강사 역할인지 확인
        SignInUtil.validateInstructor();

        // 강좌가 존재하는 강좌인지 확인하는 로직
        if (!courseDao.existById(courseId)) {
            throw new RuntimeException("존재 하지 않는 강좌입니다.");
        }

        // 해당 강좌에 섹션 번호가 이미 존재하는지 확인하는 로직
        if (sectionDao.existsByCourseIdAndOrderNum(courseId, orderNum)) {
            throw new RuntimeException("해당 강좌에 이미 존재하는 섹션 번호입니다.");
        }

        Section section = Section.createSection(courseId, title, orderNum);
        sectionDao.saveSection(section);
    }

    @Override
    public ViewSectionDto findSectionById(Long sectionId) {
        Section section = sectionDao.findSectionById(sectionId);

        if (section == null) {
            throw new RuntimeException("해당 ID의 섹션을 찾을 수 없습니다.");
        }

        return ViewSectionDto.from(section);
    }

    @Override
    public List<ViewSectionDto> findSectionsByCourseId(Long courseId) {
        // 강좌가 존재하는 강좌인지 확인하는 로직
        if (!courseDao.existById(courseId)) {
            throw new RuntimeException("ID가 " + courseId + "인 강좌를 찾을 수 없습니다.");
        }

        List<Section> sectionsByCourseId = sectionDao.findSectionsByCourseId(courseId);

        return sectionsByCourseId.stream()
                .map(ViewSectionDto::from)
                .toList();
    }

    @Override
    public void updateSection(Long sectionId, String newTitle, Integer newOrderNum) {
        // 사용자가 강사 역할인지 확인
        SignInUtil.validateInstructor();

        Section existingSection = sectionDao.findSectionById(sectionId);
        if (existingSection == null) {
            throw new RuntimeException("해당 ID의 섹션을 찾을 수 없습니다.");
        }

        // 본인이 등록한 섹션인지 확인
        Long courseId = existingSection.getCourseId();
        Course course = courseDao.findByCourseId(courseId);
        if (course.getUserId() != SignInUtil.userId) {
            throw new RuntimeException("해당 섹션을 수정할 권한이 없습니다.");
        }

        if (!existingSection.getOrderNum().equals(newOrderNum)) {
            if (sectionDao.existsByCourseIdAndOrderNum(existingSection.getCourseId(), newOrderNum)) {
                throw new RuntimeException("해당 강좌에 이미 존재하는 섹션 번호입니다.");
            }
        }

        existingSection.updateInfo(newTitle, newOrderNum);
        sectionDao.updateSection(existingSection);
    }

    @Override
    public void deleteSection(Long sectionId) {
        // 사용자가 강사 역할인지 확인
        SignInUtil.validateInstructor();

        Section existingSection = sectionDao.findSectionById(sectionId);
        if (existingSection == null) {
            throw new RuntimeException("해당 ID의 섹션을 찾을 수 없습니다.");
        }

        // 본인이 등록한 섹션인지 확인
        Long courseId = existingSection.getCourseId();
        Course course = courseDao.findByCourseId(courseId);
        if (course.getUserId() != SignInUtil.userId) {
            throw new RuntimeException("해당 섹션을 삭제할 권한이 없습니다.");
        }

        sectionDao.deleteSection(sectionId);
    }
}
