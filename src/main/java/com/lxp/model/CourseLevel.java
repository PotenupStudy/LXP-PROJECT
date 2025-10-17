package com.lxp.model;

/**
 * CourseLevel
 * 강좌 레벨
 */
public enum CourseLevel {
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");

    private final String value;

    CourseLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CourseLevel from(String name) {
        try {
            return CourseLevel.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Unknown course_level: " + name);
        }
    }

    boolean equalsName(String name){
        return this.value.equals(name);
    }
}
