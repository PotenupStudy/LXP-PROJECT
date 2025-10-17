package com.lxp.model;

import java.time.LocalDateTime;

/**
 * User Model
 * - userId     : 유저 ID
 * - name       : 유저 이름
 * - email      : 유저 이메일
 * - role       : 유저 역할 (student: 학생, instructor: 교사)
 * - insertDate : 생성 일자
 * - modifyDate : 수정 일자
 * - deleteDate : 삭제 일자
 */
public class User {

    private long userId;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private LocalDateTime deleteDate;

    /**
     * 회원가입용 생성자
     * @param name  사용자 이름
     * @param email 사용자 이메일
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(long userId, String name, String email, Role role, LocalDateTime insertDate, LocalDateTime modifyDate, LocalDateTime deleteDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.insertDate = insertDate;
        this.modifyDate = modifyDate;
        this.deleteDate = deleteDate;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
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

    /**
     * User - ToString
     * @return 유저 정보 문자열
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", insertDate=" + insertDate +
                ", modifyDate=" + modifyDate +
                ", deleteDate=" + deleteDate +
                '}';
    }
}
