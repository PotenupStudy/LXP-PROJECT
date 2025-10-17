package com.lxp.model;

public enum Role {
    STUDENT("student"),
    INSTRUCTOR("instructor"),
    ANONYMOUS("anonymous");

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
