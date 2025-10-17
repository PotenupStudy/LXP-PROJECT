package com.lxp.service;

import com.lxp.dao.SectionDao;
import com.lxp.model.Section;

import java.sql.Connection;
import java.util.List;

public class SectionServiceImpl implements SectionService {
    private final SectionDao sectionDao;

    public SectionServiceImpl(Connection connection) {
        this.sectionDao = new SectionDao(connection);
    }

    @Override
    public Boolean existsSection(Long sectionId) {
        return sectionDao.existsBySectionId(sectionId);
    }

    @Override
    public void saveSection(Long courseId, String title, Integer orderNum) {
        // 강좌가 존재하는 강좌인지 확인하는 로직

        // 해당 강좌에 섹션 번호가 이미 존재하는지 확인하는 로직
        if (sectionDao.existsByCourseIdAndOrderNum(courseId, orderNum)) {
            throw new RuntimeException("해당 강좌에 이미 존재하는 섹션 번호입니다.");
        }

        Section section = Section.createSection(courseId, title, orderNum);
        sectionDao.saveSection(section);
    }

    @Override
    public Section findSectionById(Long sectionId) {
        return sectionDao.findSectionById(sectionId);
    }

    @Override
    public List<Section> findSectionsByCourseId(Long courseId) {
        return sectionDao.findSectionsByCourseId(courseId);
    }

    @Override
    public void updateSection(Long sectionId, String newTitle, Integer newOrderNum) {
        Section existingSection = sectionDao.findSectionById(sectionId);

        if (existingSection == null) {
            throw new RuntimeException("해당 ID의 섹션을 찾을 수 없습니다.");
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
        boolean exists = sectionDao.existsBySectionId(sectionId);

        if (!exists) {
            throw new RuntimeException("해당 ID의 섹션을 찾을 수 없습니다.");
        }

        sectionDao.deleteSection(sectionId);
    }
}
