package com.lxp.model.dto;

import com.lxp.model.Role;
import com.lxp.model.User;

public class ViewUserDto {

    private final String email;
    private final String name;
    private final Role role;

    public ViewUserDto(String email, String name, Role role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    /**
     * User -> ViewUserDto 변환
     * @param user 유저 com.lxp.model
     * @return ViewUserDto
     */
    public static ViewUserDto from(User user) {
        return  new ViewUserDto(
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }

    @Override
    public String toString() {
        return "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role;
    }
}
