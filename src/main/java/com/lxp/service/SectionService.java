package com.lxp.service;

import com.lxp.model.dto.ViewSectionDto;

import java.util.List;

public interface SectionService {
    Boolean existsSection(Long sectionId);

    ViewSectionDto findSectionById(Long sectionId);

    List<ViewSectionDto> findSectionsByCourseId(Long courseId);

    void saveSection(Long courseId, String title, Integer orderNum);

    void updateSection(Long sectionId, String newTitle, Integer newOrderNum);

    void deleteSection(Long sectionId);
}
