package com.lxp.model.dto;

import com.lxp.model.Section;

public class ViewSectionDto {
    private Long sectionId;
    private String title;
    private Integer orderNum;

    private ViewSectionDto(Long sectionId, String title, Integer orderNum) {
        this.sectionId = sectionId;
        this.title = title;
        this.orderNum = orderNum;
    }

    public static ViewSectionDto from(Section section) {
        if (section == null) {
            return null;
        }

        return new ViewSectionDto(
                section.getSectionId(),
                section.getTitle(),
                section.getOrderNum()
        );
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    @Override
    public String toString() {
        return "ViewSectionDto{" +
                "sectionId=" + sectionId +
                ", title='" + title + '\'' +
                ", orderNum=" + orderNum +
                '}';
    }
}
