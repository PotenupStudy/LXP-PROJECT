package com.lxp.model;

import java.time.LocalDateTime;

public class Lecture {
    private Long lecture_id;
    private Long section_id;
    private String title;
    private int lecture_order;
    private String description;
    private LocalDateTime insert_date;
    private LocalDateTime modify_date;



    public Lecture(Long section_id, Long lecture_id) {
        this.section_id = section_id;
        this.lecture_id = lecture_id;
    }

    public Lecture(Long section_id, String title, String description, int lecture_order) {
        this.section_id = section_id;
        this.title = title;
        this.lecture_order = lecture_order;
        this.description = description;
    }

    // 조회 생성자
    public Lecture(Long lecture_id, Long section_id, String title, String description, int lecture_order) {
        this.lecture_id = lecture_id;
        this.section_id = section_id;
        this.title = title;
        this.lecture_order = lecture_order;
        this.description = description;
    }

    // 수정 생성자
    public Lecture(String description, int lecture_order, String title, Long section_id, Long lecture_id) {
        this.description = description;
        this.lecture_order = lecture_order;
        this.title = title;
        this.section_id = section_id;
        this.lecture_id = lecture_id;
    }

    public Long getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(Long lecture_id) {
        this.lecture_id = lecture_id;
    }

    public Long getSection_id() {
        return section_id;
    }

    public void setSection_id(Long section_id) {
        this.section_id = section_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLecture_order() {
        return lecture_order;
    }

    public void setLecture_order(int lecture_order) {
        this.lecture_order = lecture_order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(LocalDateTime insert_date) {
        this.insert_date = insert_date;
    }

    public LocalDateTime getModify_date() {
        return modify_date;
    }

    public void setModify_date(LocalDateTime modify_date) {
        this.modify_date = modify_date;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "lecture_id=" + lecture_id +
                ", section_id=" + section_id +
                ", title='" + title + '\'' +
                ", lecture_order=" + lecture_order +
                ", description='" + description + '\'' +
                ", insert_date=" + insert_date +
                ", modify_date=" + modify_date +
                '}';
    }
}
