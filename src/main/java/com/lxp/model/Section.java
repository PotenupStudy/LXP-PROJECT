package com.lxp.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Section {
    private Long sectionId;
    private Long courseId;
    private String title;
    private Integer orderNum;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;

    public static Section createSection(Long courseId, String title, Integer orderNum) {
        validateForCreate(courseId, title, orderNum);
        return new Section(courseId, title, orderNum);
    }

    public static Section from(Long sectionId, Long courseId, String title, Integer orderNum) {
        return new Section(sectionId, courseId, title, orderNum);
    }

    private Section(Long courseId, String title, Integer orderNum) {
        this.courseId = courseId;
        this.title = title;
        this.orderNum = orderNum;
        this.insertDate = LocalDateTime.now();
    }

    private Section(Long sectionId, Long courseId, String title, Integer orderNum) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.title = title;
        this.orderNum = orderNum;
    }

    private static void validateForCreate(Long courseId, String title, Integer orderNum) {
        validateCourseId(courseId);
        validateTitle(title);
        validateOrderNum(orderNum);
    }

    public void updateInfo(String newTitle, Integer newOrderNum) {
        validateTitle(newTitle);
        validateOrderNum(newOrderNum);

        this.title = newTitle;
        this.orderNum = newOrderNum;
        this.modifyDate = LocalDateTime.now();
    }

    /**
     * 강좌 ID 검증 (필수, 양수)
     */
    private static void validateCourseId(Long courseId) {
        Objects.requireNonNull(courseId, "강좌 ID는 필수입니다.");

        if (courseId <= 0) {
            throw new IllegalArgumentException("강좌 ID는 반드시 양수입니다.");
        }
    }

    /**
     * 색션명 검증 (필수, 길이 100 이하)
     */
    private static void validateTitle(String title) {
        Objects.requireNonNull(title, "섹션 제목은 필수입니다.");

        if (title.length() > 100) {
            throw new IllegalArgumentException("섹션 제목은 100자를 넘어가면 안됩니다.");
        }
    }

    /**
     * 섹션 번호 검증 (필수, 양수)
     */
    private static void validateOrderNum(Integer orderNum) {
        Objects.requireNonNull(orderNum, "섹션 번호는 필수입니다.");

        if (orderNum <= 0) {
            throw new IllegalArgumentException("섹션 번호는 반드시 양수입니다.");
        }
    }

    public Long getSectionId() {
        return sectionId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    @Override
    public String toString() {
        return """
                Section {
                  sectionId  = %d,
                  courseId   = %d,
                  title      = '%s',
                  orderNum   = %d,
                  insertDate = %s,
                  modifyDate = %s
                }""".formatted(sectionId, courseId, title, orderNum, insertDate, modifyDate);
    }
}
