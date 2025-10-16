package model.dto;

import model.Role;

public class RegisterUserDto {

    private final String name;
    private final String email;
    private final Role role;

    public RegisterUserDto(String name, String email, String roleStr) {
        this.name = name;
        this.email = email;
        this.role = roleStr.equals("1")? Role.STUDENT :
                roleStr.equals("2")? Role.INSTRUCTOR : Role.ANONYMOUS;
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role;
    }
}
