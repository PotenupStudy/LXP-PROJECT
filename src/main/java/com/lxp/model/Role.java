package com.lxp.model;

public enum Role {
    STUDENT("student"),         // 학생
    INSTRUCTOR("instructor"),   // 교사
    ADMIN("admin"),             // 관리자
    ANONYMOUS("anonymous");     // 잘못 입력된 경우를 대비한 값

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }

    public static Role findByString(String role) {
        for(Role r : Role.values()) {
            if(r.role.equals(role)) {
                return r;
            }
        }

        return null;
    }
}
