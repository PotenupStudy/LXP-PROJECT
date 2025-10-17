package com.lxp.service;

import com.lxp.model.Section;

import java.util.List;

public interface SectionService {
    Boolean existsSection(Long sectionId);

    Section findSectionById(Long sectionId);

    List<Section> findSectionsByCourseId(Long courseId);

    void saveSection(Long courseId, String title, Integer orderNum);

    void updateSection(Long sectionId, String newTitle, Integer newOrderNum);

    void deleteSection(Long sectionId);
}
