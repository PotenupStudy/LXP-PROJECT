package com.lxp.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Course {
    private Long courseId;
    private Long userId;
    private Long categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private CourseLevel courseLevel;

    public static Course createCourse(Long userId, Long categoryId, String title,
                                      String description, BigDecimal price, CourseLevel courseLevel) {
        validateForCreate(userId, categoryId, title, price, courseLevel);
        return new Course(userId, categoryId, title, description, price, courseLevel);
    }

    private Course(Long userId, Long categoryId, String title, String description, BigDecimal price, CourseLevel courseLevel) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.courseLevel = courseLevel;
    }

    public Course(Long courseId, Long userId, Long categoryId, String title, String description, BigDecimal price, CourseLevel courseLevel) {
        this.courseId = courseId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.courseLevel = courseLevel;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        this.courseLevel = courseLevel;
    }

    /**
     * 강좌 생성시 필수 필드 검증
     */
   private static void validateForCreate(Long userId, Long categoryId, String title, BigDecimal price, CourseLevel courseLevel){
        validateUserId(userId);
        validateCategoryId(categoryId);
        validatePrice(price);
        validateTitle(title);
        Objects.requireNonNull(courseLevel, "courseLevel is required");
    }


    /**
     * 강좌 수정시 필수 필드 검증
     */
    public void validateForUpdate(){
        validateCourseId(this.courseId);
        validateCategoryId(this.categoryId);
        validatePrice(this.price);
        validateTitle(this.title);
        Objects.requireNonNull(this.courseLevel, "courseLevel is required");
    }

    /**
     * 강좌 삭제시 필수 필드 검증
     */
    public void validateForDelete(){
        validateCourseId(this.courseId);
    }

    /**
     * 강좌 ID 검증 (양수)
     */
    private static void validateCourseId(Long courseId) {
        if (courseId <= 0)
            throw new IllegalArgumentException("courseId must be positive");
    }
    /**
     * 사용자 ID 검증 (양수)
     */
    private static void validateUserId(Long userId) {
        if (userId <= 0)
            throw new IllegalArgumentException("userId must be positive");
    }

    /**
     * 카테고리 ID 검증 (양수)
     */
    private static void validateCategoryId(Long categoryId) {
        if (categoryId <= 0)
            throw new IllegalArgumentException("categoryId must be positive");
    }

    /**
     * 가격 검증 (필수, 0 이상)
     */
    private static void validatePrice(BigDecimal price) {
        Objects.requireNonNull(price, "price cannot be null");

        if (price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("price cannot be negative");
    }

    /**
     * 강좌명 검증 (필수, 길이 100 이하)
     */
    private static void validateTitle(String title) {
        Objects.requireNonNull(title, "title is required");

        if (title.length() > 100)
            throw new IllegalArgumentException("title must not exceed 100 characters");
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", courseLevel=" + courseLevel +
                '}';
    }
}
