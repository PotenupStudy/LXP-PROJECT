package com.lxp.model;

import java.time.LocalDateTime;

public class Section {
    private long sectionId;
    private long courseId;
    private String title;
    private int orderNum;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private LocalDateTime deleteDate;

    public Section(long courseId, String title, int orderNum) {
        this.courseId = courseId;
        this.title = title;
        this.orderNum = orderNum;
        this.insertDate = LocalDateTime.now();
    }

    // Getter 및 Setter 메소드
    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    // toString 메소드
    @Override
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", orderNum=" + orderNum +
                ", insertDate=" + insertDate +
                ", modifyDate=" + modifyDate +
                ", deleteDate=" + deleteDate +
                '}';
    }
}
